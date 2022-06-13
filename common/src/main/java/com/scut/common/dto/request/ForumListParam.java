package com.scut.common.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "获得论坛列表参数")
public class ForumListParam {
    @ApiModelProperty(value = "第几页")
    private int page;
    @ApiModelProperty(value = "页大小")
    private int size;
    @ApiModelProperty(value = "搜索关键字")
    private int searchText;
}
