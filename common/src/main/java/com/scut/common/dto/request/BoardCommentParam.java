package com.scut.common.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "评论")
public class BoardCommentParam {
    @ApiModelProperty(value = "游戏ID",required = true)
    long id;
    @ApiModelProperty(value = "评价分数",required = true)
    long score;
    @ApiModelProperty(value = "评价内容")
    String comment;
}
