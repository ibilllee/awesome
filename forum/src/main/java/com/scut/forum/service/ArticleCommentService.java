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
import com.scut.forum.feign.UserFeignService;
import com.scut.forum.mapper.ArticleCommentMapper;
import com.scut.forum.mapper.ArticleMapper;
import com.scut.forum.util.HotIndexUtil;
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
    @Resource
    private UserFeignService userFeignService;

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
        if (replyId == null || articleCommentParam.getReplyId() == -1)
            articleComment.setReplyId(-1L);
        else if (replyComment == null)
            return new ArticleCommentDto(-1);
        else if (replyComment.getReplyId() != -1)
            return new ArticleCommentDto(-3);

        int count = articleCommentMapper.insert(articleComment);
        if (count == 1) {
            articleMapper.updateUpdateTime(articleId, nowTime);//?????????????????????????????????
            if (replyId != null && replyId != -1) {//????????????
                articleCommentMapper.updateReplyCount(replyId, 1);//???????????????????????????
                InformDto informDto = new InformDto(replyComment.getUserId(),
                        "????????????",
                        "???????????????" + article.getTitle() + "?????????????????????????????????");
                rocketMQTemplate.convertAndSend(MQConstant.TOPIC_PUSH_INFORM, JSON.toJSONBytes(informDto));
            } else {//????????????
                InformDto informDto = new InformDto(
                        article.getUserId(),
                        "????????????",
                        "???????????????" + article.getTitle() + "??????????????????");
                rocketMQTemplate.convertAndSend(MQConstant.TOPIC_PUSH_INFORM, JSON.toJSONBytes(informDto));
                HotIndexUtil.updateArticleHotIndex(article);
            }

            return articleComment.getDto(userFeignService.getAvatarAndUsername(userId).getData());
        }
        return null;
    }

    public ArticleCommentDto get(long id) {
        ArticleComment articleComment = articleCommentMapper.selectById(id);
        if (articleComment == null)
            return null;
        return articleComment.getDto(userFeignService.getAvatarAndUsername(articleComment.getUserId()).getData());
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
            articleDtos.add(articleComment.getDto(userFeignService.getAvatarAndUsername(articleComment.getUserId()).getData()));
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
            articleDtos.add(articleComment.getDto(userFeignService.getAvatarAndUsername(articleComment.getUserId()).getData()));
        }
        return articleDtos;
    }
}
