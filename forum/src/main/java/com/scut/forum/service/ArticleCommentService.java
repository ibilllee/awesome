package com.scut.forum.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scut.common.constant.MQConstant;
import com.scut.common.dto.request.ArticleCommentListParam;
import com.scut.common.dto.request.ArticleCommentParam;
import com.scut.common.dto.request.ArticleParam;
import com.scut.common.dto.request.ForumTagParam;
import com.scut.common.dto.response.ArticleCommentDto;
import com.scut.common.dto.response.ArticleDto;
import com.scut.common.dto.response.InformDto;
import com.scut.forum.entity.Article;
import com.scut.forum.entity.ArticleComment;
import com.scut.forum.mapper.ArticleCommentMapper;
import com.scut.forum.mapper.ArticleMapper;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleCommentService {
    @Resource
    private ArticleCommentMapper articleCommentMapper;
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Transactional
    public ArticleCommentDto submit(ArticleCommentParam articleCommentParam, long userId) {
        ArticleComment articleComment = new ArticleComment(articleCommentParam);
        articleComment.setUserId(userId);
        articleComment.setReplyCount(0L);
        long nowTime = System.currentTimeMillis();
        articleComment.setCreateTime(nowTime);

        Long articleId = articleComment.getArticleId();
        Article article = articleMapper.selectById(articleId);
        if (article == null)
            return new ArticleCommentDto(-2);

        Long replyId = articleCommentParam.getReplyId();
        ArticleComment replyComment = articleCommentMapper.selectById(replyId);
        if (replyId == null)
            articleComment.setReplyId(-1L);
        else if (replyComment == null)
            return new ArticleCommentDto(-1);
        else if (replyComment.getReplyId() != -1)
            return new ArticleCommentDto(-3);

        int count = articleCommentMapper.insert(articleComment);
        if (count == 1) {
            articleMapper.updateUpdateTime(articleId, nowTime);//更新文章的最后更新时间
            if (replyId != null) {//二级评论
                articleCommentMapper.updateReplyCount(replyId, 1);//更新父评论的回复数
                InformDto informDto = new InformDto(replyComment.getUserId(),
                        "评论通知",
                        "你在文章《" + article.getTitle() + "》中的评论有了新的回复");
                rocketMQTemplate.convertAndSend(MQConstant.TOPIC_PUSH_INFORM, JSON.toJSONBytes(informDto));
            } else {//一级评论
                InformDto informDto = new InformDto(
                        article.getUserId(),
                        "评论通知",
                        "你的文章《" + article.getTitle() + "》有新的评论");
                rocketMQTemplate.convertAndSend(MQConstant.TOPIC_PUSH_INFORM, JSON.toJSONBytes(informDto));
            }
            return articleComment.getDto();
        }
        return null;
    }

    public ArticleCommentDto get(long id) {
        ArticleComment articleComment = articleCommentMapper.selectById(id);
        if (articleComment == null)
            return null;
        return articleComment.getDto();
    }

    @Transactional
    public int delete(long id, long userId) {
        ArticleComment articleComment = articleCommentMapper.selectById(id);
        if (articleComment == null) return 0;
        if (articleComment.getUserId() != userId) return -1;

        int count = articleCommentMapper.deleteByParentId(id);
        articleCommentMapper.deleteById(id);
        return count + 1;
    }


    public List<ArticleCommentDto> list(ArticleCommentListParam articleCommentListParam, long articleId) {
        Page<ArticleComment> articlePage = new Page<>(articleCommentListParam.getPage(), articleCommentListParam.getSize());
        Page<ArticleComment> page = new LambdaQueryChainWrapper<>(articleCommentMapper)
                .eq(ArticleComment::getArticleId, articleId)
                .eq(ArticleComment::getReplyId, -1L)
                .orderByDesc(ArticleComment::getCreateTime)
                .page(articlePage);
        List<ArticleComment> articleComments = page.getRecords();
        List<ArticleCommentDto> articleDtos = new ArrayList<>();
        for (ArticleComment articleComment : articleComments) {
            articleDtos.add(articleComment.getDto());
        }
        return articleDtos;
    }

    public List<ArticleCommentDto> listByParentId(ArticleCommentListParam articleCommentListParam, long articleId, long replyId) {
        Page<ArticleComment> articlePage = new Page<>(articleCommentListParam.getPage(), articleCommentListParam.getSize());
        Page<ArticleComment> page = new LambdaQueryChainWrapper<>(articleCommentMapper)
                .eq(ArticleComment::getArticleId, articleId)
                .eq(ArticleComment::getReplyId, replyId)
                .orderByDesc(ArticleComment::getCreateTime)
                .page(articlePage);
        List<ArticleComment> articleComments = page.getRecords();
        List<ArticleCommentDto> articleDtos = new ArrayList<>();
        for (ArticleComment articleComment : articleComments) {
            articleDtos.add(articleComment.getDto());
        }
        return articleDtos;
    }
}
