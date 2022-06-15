package com.scut.common.dto.response;

import com.scut.common.dto.request.ArticleParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
    private long id;
    private long userId;
    private long forumId;
    private String title;
    private String content;
    private long likeCount;
    private long favorCount;
    private long createTime;
    private long updateTime;
    private String tag;
    private long viewCount;
    private String attachment;
}
