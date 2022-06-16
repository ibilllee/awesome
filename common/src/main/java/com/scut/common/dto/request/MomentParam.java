package com.scut.common.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "提交动态参数")
public class MomentParam {
    @ApiModelProperty(value = "动态内容", required = true)
    private String content;
    @ApiModelProperty(value = "附件 (JSON化后的图片地址等)", required = true)
    private String attachment;
}
