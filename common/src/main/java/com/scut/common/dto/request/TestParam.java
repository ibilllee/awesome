package com.scut.common.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "测试参数")
public class TestParam {
    @ApiModelProperty(value = "用户ID")
    String userId;
    @ApiModelProperty(value = "姓名",required = true)
    String name;
}
