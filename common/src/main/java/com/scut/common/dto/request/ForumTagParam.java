package com.scut.common.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "提交论坛标签参数")
public class ForumTagParam {
    @ApiModelProperty(value = "对应论坛ID", required = true)
    private long forumId;
    @ApiModelProperty(value = "标签内容", required = true)
    private String content;
}
