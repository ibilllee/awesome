package com.scut.board.controller;

import com.scut.common.dto.request.RegisterAndLoginParam;
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
    public SingleResponse<UserDto> updateUsername(@RequestBody String username) {
        return null;
    }

    @PutMapping("/update/introduce")
    @ApiOperation(value = "/update/introduce", notes = "更新个性签名")
    public SingleResponse<UserDto> updateIntroduce(@RequestBody String introduce) {
        return null;
    }

    @PutMapping("/update/avatar")
    @ApiOperation(value = "/update/avatar", notes = "更新头像地址")
    public SingleResponse<UserDto> updateAvatar(@RequestBody String avatar) {
        return null;
    }

    @PutMapping("/update/password")
    @ApiOperation(value = "/update/password", notes = "修改密码")
    public SingleResponse<UserDto> updatePassword(@RequestBody String password) {
        return null;
    }

    @PostMapping("/retrieve")
    @ApiOperation(value = "/retrieve", notes = "找回密码")
    public SingleResponse<String> retrievePassword() {
        //1、生成token
        //2、存入redis
        //3、生成url:/interactive?token=......
        //4、发送邮件
        //5、返回发送邮件的目的邮箱（返回值）
        return null;
    }
}
