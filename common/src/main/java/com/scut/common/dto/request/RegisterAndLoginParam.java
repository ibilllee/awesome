package com.scut.common.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "提交注册/登录的邮箱、密码")
public class RegisterAndLoginParam {
    @ApiModelProperty(value = "邮箱",required = true)
    String email;
    @ApiModelProperty(value = "密码",required = true)
    String password;
}
