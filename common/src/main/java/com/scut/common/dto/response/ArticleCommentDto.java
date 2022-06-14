package com.scut.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCommentDto {
    private long id;
    private long userId;
    private long articleId;
    private String content;
    private long replyId;
    private long replyCount;
    private long createTime;

    public ArticleCommentDto(long id) {
        this.id = id;
    }
}
