package com.scut.common.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "获得文章列表参数")
public class ArticleListForMeParam {
    @ApiModelProperty(value = "第几页", required = true)
    private int page;
    @ApiModelProperty(value = "页大小", required = true)
    private int size;
}
