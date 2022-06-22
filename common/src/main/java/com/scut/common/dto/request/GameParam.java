package com.scut.common.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "游戏信息")
public class GameParam {
    @ApiModelProperty(value = "游戏名称", required = true)
    String name;
    @ApiModelProperty(value = "游戏封面地址", required = true)
    String cover;
    @ApiModelProperty(value = "游戏logo地址", required = true)
    String logo;
    @ApiModelProperty(value = "游戏上架时间", required = true)
    long issuedTime;
    @ApiModelProperty(value = "下载链接", required = true)
    String downloadLink;
    @ApiModelProperty(value = "游戏分类", required = true)
    String classify;
    @ApiModelProperty(value = "游戏描述")
    String description;
}