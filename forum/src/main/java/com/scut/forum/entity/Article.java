package com.scut.forum.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.scut.common.dto.request.ArticleParam;
import com.scut.common.dto.response.ArticleDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("article")
public class Article {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long forumId;
    private String title;
    private String content;
    private Long likeCount;
    private Long favorCount;
    private Long createTime;
    private Long updateTime;
    private String tag;
    private Long viewCount;
    private String attachment;

    public Article(ArticleParam articleParam) {
        forumId = articleParam.getForumId();
        title = articleParam.getTitle();
        content = articleParam.getContent();
        likeCount = 0L;
        favorCount = 0L;
        createTime = updateTime = System.currentTimeMillis();
        tag = articleParam.getTag();
        viewCount = 0L;
        attachment = "";
    }

    public ArticleDto getDto() {
        return new ArticleDto(getId(),
                getUserId(),
                getForumId(),
                getTitle(),
                getContent(),
                getLikeCount(),
                getFavorCount(),
                getCreateTime(),
                getUpdateTime(),
                getTag(),
                getViewCount(),
                getAttachment());
    }
}
