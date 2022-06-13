package com.scut.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Api(value = "interactive", description = "点击邮件中的链接")
@Slf4j
public class PasswordController {
    @PostMapping("/interactive")
    @ApiOperation(value = "/interactive", notes = "找回密码的交互界面")
    public String interactiveInterface() {
        /*
        * 返回值为一个String串，经过视图解析器解析跳转到对应的界面
        * 用户点击邮箱中的url地址会跳转到这个界面，在这个界面中完成新密码的设置
        * html页面要完成的任务是当用户点击页面中的“确认”按钮后
        * 将用户重新设置的密码作为参数，发送请求到/user/update/password
        * */
        return null;
    }
}
