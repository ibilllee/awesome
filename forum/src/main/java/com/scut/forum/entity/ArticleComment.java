package com.scut.forum.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.scut.common.dto.request.ArticleCommentParam;
import com.scut.common.dto.response.ArticleCommentDto;
import com.scut.common.dto.response.ArticleDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("article_comment")
public class ArticleComment {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long articleId;
    private String content;
    private Long replyId;
    private Long replyCount;
    private Long createTime;

    public ArticleComment(ArticleCommentParam articleCommentParam) {
        articleId = articleCommentParam.getArticleId();
        content = articleCommentParam.getContent();
        replyId = articleCommentParam.getReplyId();
    }

    public ArticleCommentDto getDto() {
        return new ArticleCommentDto(getId(),
                getUserId(),
                getArticleId(),
                getContent(),
                getReplyId(),
                getReplyCount(),
                getCreateTime());
    }
}
