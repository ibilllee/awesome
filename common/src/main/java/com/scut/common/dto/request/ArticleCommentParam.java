package com.scut.common.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "提交文章参数")
public class ArticleCommentParam {
    @ApiModelProperty(value = "文章ID", required = true)
    private long articleId;
    @ApiModelProperty(value = "评论内容", required = true)
    private String content;
    @ApiModelProperty(value = "二级消息回复评论ID，一级消息不传")
    private Long replyId;
}
