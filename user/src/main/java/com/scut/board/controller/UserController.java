package com.scut.board.controller;

import com.scut.common.dto.request.*;
import com.scut.common.dto.response.TokenDto;
import com.scut.common.dto.response.UserDto;
import com.scut.common.response.SingleResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(value = "user", description = "用户")
@Slf4j
public class UserController {

    @PostMapping("/submit")
    @ApiOperation(value = "/submit",notes = "注册新用户")
    public SingleResponse<String> submit(@RequestBody RegisterAndLoginParam registerParam){
        //返回结果是调用ok()的返回值，String为“”，即data为空数据
        return null;
    }

    @PostMapping("/login")
    @ApiOperation(value = "/login",notes = "登录")
    public SingleResponse<TokenDto> login(@RequestBody RegisterAndLoginParam loginParam){
        //返回结果是调用ok()的返回值，TokenDto为返回给前端的token串
        return null;
    }

    @PostMapping("/logout")
    @ApiOperation(value = "/logout",notes = "退出登录")
    public SingleResponse<String> login(){
        //参数是@RequestHeader(value="USERNAME")String username
        //返回值String为""
        return null;
    }

    @PostMapping("/get/info")
    @ApiOperation(value = "/get/user", notes = "获取用户相关信息")
    public SingleResponse<UserDto> getUserInfo() {
        //参数是@RequestHeader(value="USERNAME")String username
        return null;
    }

    @PutMapping("/update/username")
    @ApiOperation(value = "/update/username", notes = "更新用户名")
    public SingleResponse<UserDto> updateUsername(@RequestBody UsernameParam usernameParam) {
        return null;
    }

    @PutMapping("/update/introduce")
    @ApiOperation(value = "/update/introduce", notes = "更新个性签名")
    public SingleResponse<UserDto> updateIntroduce(@RequestBody IntroduceParam introduceParam) {
        return null;
    }

    @PutMapping("/update/avatar")
    @ApiOperation(value = "/update/avatar", notes = "更新头像地址")
    public SingleResponse<UserDto> updateAvatar(@RequestBody AvatarParam avatarParam) {
        return null;
    }

    @PutMapping("/update/password")
    @ApiOperation(value = "/update/password", notes = "修改密码")
    public SingleResponse<UserDto> updatePassword(@RequestBody PasswordParam passwordParam) {
        return null;
    }

    @PostMapping("/retrieve")
    @ApiOperation(value = "/retrieve", notes = "找回密码")
    public SingleResponse<String> retrievePassword() {
        //客户端在点击“找回密码”后发送的请求
        //1、生成验证码：A，token:B
        //2、存入redis：(key,value)=(B,A)
        //3、发送邮件,邮件里面包含了验证码：A
        //5、返回值包含：1、发送邮件的目的邮箱。2、token串
        return null;
    }

    @PostMapping("/verify")
    @ApiOperation(value = "/verify", notes = "判断验证码是否正确")
    public SingleResponse<String> verifyCorrectness(@RequestBody VerificationCodeParam verificationCodeParam) {
        /*
         * 客户端在点击“找回密码”后进入新的界面
         * 在新的界面中填写新密码，确认密码，填写验证码
         * 点击“确认按钮”后发送“/verify”请求向后端发送以上数据以及token串
         * */
        //1、设传入的验证码为A',通过token B,从Redis中获取验证码A
        //2、判断A'和A是否相等
        //3、若相等，转发请求到/update/password，返回的String提示用户成功
        //4、若不相等，返回String提示用户验证码错误
        return null;
    }
}
