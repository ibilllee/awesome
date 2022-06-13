package com.scut.common.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "提交动态评论参数")
public class MomentCommentParam {
    @ApiModelProperty(value = "动态ID", required = true)
    private long momentId;
    @ApiModelProperty(value = "评论内容", required = true)
    private String content;
    @ApiModelProperty(value = "回复评论ID", required = true)
    private long replyId;
}
