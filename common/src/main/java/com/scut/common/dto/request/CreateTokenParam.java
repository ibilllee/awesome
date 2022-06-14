package com.scut.common.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "生成Token时传入的参数")
public class CreateTokenParam {
    @ApiModelProperty(value = "用户ID",required = true)
    long id;
    @ApiModelProperty(value = "邮箱",required = true)
    String email;
    @ApiModelProperty(value = "用户名")
    String username;
}
