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
public class ArticleParam {
    @ApiModelProperty(value = "用户ID", required = true)
    private long userId;
    @ApiModelProperty(value = "文章标题", required = true)
    private String title;
    @ApiModelProperty(value = "文章内容", required = true)
    private String content;
    @ApiModelProperty(value = "文章标签", required = true)
    private String tag;
}
