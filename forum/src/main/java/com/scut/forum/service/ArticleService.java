package com.scut.forum.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scut.common.constant.MQConstant;
import com.scut.common.dto.request.ArticleListForMeParam;
import com.scut.common.dto.request.ArticleListParam;
import com.scut.common.dto.request.ArticleParam;
import com.scut.common.dto.request.ForumTagParam;
import com.scut.common.dto.response.ArticleDto;
import com.scut.common.dto.response.InformDto;
import com.scut.forum.entity.*;
import com.scut.forum.mapper.ArticleFavorMapper;
import com.scut.forum.mapper.ArticleLikeMapper;
import com.scut.forum.mapper.ArticleMapper;
import com.scut.forum.mapper.ForumMapper;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {
    @Resource
    private ForumMapper forumMapper;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private ArticleLikeMapper articleLikeMapper;

    @Resource
    private ArticleFavorMapper articleFavorMapper;

    @Resource
    private ForumService forumService;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Transactional
    public ArticleDto submit(ArticleParam articleParam, Long userId) {
        Article article = new Article(articleParam);
        article.setUserId(userId);
        int count = articleMapper.insert(article);
        if (count == 1) {
            forumService.submitTag(new ForumTagParam(article.getForumId(), article.getTag()));
            forumMapper.updateArticleCount(articleParam.getForumId(), 1);
            return article.getDto();
        }
        return null;
    }

    public ArticleDto get(long id) {
        Article article = articleMapper.selectById(id);
        if (article == null)
            return null;
        return article.getDto();
    }

    public List<ArticleDto> getListByTime(ArticleListParam articleListParam) {
        Page<Article> articlePage = new Page<>(articleListParam.getPage(), articleListParam.getSize());
        Page<Article> page = new LambdaQueryChainWrapper<>(articleMapper)
                .eq(Article::getForumId, articleListParam.getForumId())
                .orderByDesc(Article::getCreateTime)
                .page(articlePage);
        List<Article> articles = page.getRecords();
        List<ArticleDto> articleDtos = new ArrayList<>();
        for (Article article : articles) {
            articleDtos.add(article.getDto());
        }
        return articleDtos;
    }

    public List<ArticleDto> getMyList(ArticleListForMeParam articleListForMeParam, Long userId) {
        Page<Article> articlePage = new Page<>(articleListForMeParam.getPage(), articleListForMeParam.getSize());
        Page<Article> page = new LambdaQueryChainWrapper<>(articleMapper)
                .eq(Article::getUserId, userId)
                .orderByDesc(Article::getCreateTime)
                .page(articlePage);
        List<Article> articles = page.getRecords();
        List<ArticleDto> articleDtos = new ArrayList<>();
        for (Article article : articles) {
            articleDtos.add(article.getDto());
        }
        return articleDtos;
    }

    public int delete(long id, long userId) {
        Article article = articleMapper.selectById(id);
        if (article == null) return 0;
        if (article.getUserId() != userId) return -1;
        return articleMapper.deleteById(id);
    }

    @Transactional
    public int favor(long id, long userId) {
        Article article = articleMapper.selectById(id);
        if (article == null) return -1;
        ArticleFavor articleFavor = articleFavorMapper.selectOne(new QueryWrapper<ArticleFavor>()
                .eq("article_id", id).eq("user_id", userId));
        if (articleFavor == null) {
            articleFavor = new ArticleFavor(0L, userId, id);
            if (articleFavorMapper.insert(articleFavor) == 1) {
                articleMapper.updateFavorCount(id, 1);

                //给文章作者发通知
                InformDto informDto = new InformDto(
                        article.getUserId(),
                        "收藏通知",
                        "你的文章《" + article.getTitle() + "》有新增收藏");
                rocketMQTemplate.convertAndSend(MQConstant.TOPIC_PUSH_INFORM, JSON.toJSONBytes(informDto));

                return 1;
            } else {
                return 0;
            }
        } else {
            return -2;
        }
    }

    @Transactional
    public int unfavor(long id, long userId) {
        if (articleMapper.selectById(id) == null) return -1;
        ArticleFavor articleFavor = articleFavorMapper.selectOne(new QueryWrapper<ArticleFavor>()
                .eq("article_id", id).eq("user_id", userId));
        if (articleFavor == null) {
            return -2;
        } else {
            if (articleFavorMapper.delete(new QueryWrapper<ArticleFavor>()
                    .eq("article_id", id).eq("user_id", userId)) == 1) {
                articleMapper.updateFavorCount(id, -1);
                return 1;
            } else {
                return 0;
            }
        }
    }

    public boolean isFavor(long userId, long id) {
        return articleFavorMapper.selectOne(new QueryWrapper<ArticleFavor>()
                .eq("article_id", id).eq("user_id", userId)) != null;
    }

    @Transactional
    public int like(long id, long userId) {
        Article article = articleMapper.selectById(id);
        if (article == null) return -1;
        ArticleLike articleLike = articleLikeMapper.selectOne(new QueryWrapper<ArticleLike>()
                .eq("article_id", id).eq("user_id", userId));
        if (articleLike == null) {
            articleLike = new ArticleLike(0L, userId, id);
            if (articleLikeMapper.insert(articleLike) == 1) {
                articleMapper.updateLikeCount(id, 1);

                //给文章作者发通知
                InformDto informDto = new InformDto(
                        article.getUserId(),
                        "点赞通知",
                        "你的文章《" + article.getTitle() + "》有新增点赞");
                rocketMQTemplate.convertAndSend(MQConstant.TOPIC_PUSH_INFORM, JSON.toJSONBytes(informDto));

                return 1;
            } else {
                return 0;
            }
        } else {
            return -2;
        }
    }

    @Transactional
    public int unlike(long id, long userId) {
        if (articleMapper.selectById(id) == null) return -1;
        ArticleLike articleLike = articleLikeMapper.selectOne(new QueryWrapper<ArticleLike>()
                .eq("article_id", id).eq("user_id", userId));
        if (articleLike == null) {
            return -2;
        } else {
            if (articleLikeMapper.delete(new QueryWrapper<ArticleLike>()
                    .eq("article_id", id).eq("user_id", userId)) == 1) {
                articleMapper.updateLikeCount(id, -1);
                return 1;
            } else {
                return 0;
            }
        }
    }

    public boolean isLike(long userId, long id) {
        return articleLikeMapper.selectOne(new QueryWrapper<ArticleLike>()
                .eq("article_id", id).eq("user_id", userId)) != null;
    }


    public List<ArticleDto> favorList(ArticleListForMeParam articleListForMeParam, long userId) {
        Page<Article> articlePage = new Page<>(articleListForMeParam.getPage(), articleListForMeParam.getSize());
        Page<Article> page = new LambdaQueryChainWrapper<>(articleMapper)
                .exists(" SELECT 1 FROM article_favor WHERE article_id = article.id AND user_id = " + userId)
                .orderByDesc(Article::getCreateTime)
                .page(articlePage);
        List<Article> articles = page.getRecords();
        List<ArticleDto> articleDtos = new ArrayList<>();
        for (Article article : articles) {
            articleDtos.add(article.getDto());
        }
        return articleDtos;
    }

    public int view(long id) {
        if (articleMapper.selectById(id) == null) return -1;
        return articleMapper.updateViewCount(id, 1) == 1 ? 1 : 0;
    }
}
