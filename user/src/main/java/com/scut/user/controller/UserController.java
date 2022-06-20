package com.scut.user.controller;

import com.alibaba.fastjson.JSON;
import com.scut.common.constant.MQConstant;
import com.scut.common.dto.request.*;
import com.scut.common.dto.response.*;
import com.scut.common.response.MultiResponse;
import com.scut.common.response.SingleResponse;
import com.scut.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.scut.common.constant.HttpConstant.USER_ID_HEADER;

@RestController
@RequestMapping("/user")
@Api(value = "user", description = "用户")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @PostMapping("/submit")
    @ApiOperation(value = "/submit", notes = "注册新用户")
    public SingleResponse<String> submit(@RequestBody RegisterAndLoginParam registerParam) throws Exception {
        int result = userService.register(registerParam);
        if (result == -1)
            return new SingleResponse<String>().error(null, 1005, "注册邮箱格式错误");
        else if (result == -2)
            return new SingleResponse<String>().error(null, 1002, "注册密码格式错误");
        else if (result == -3)
            return new SingleResponse<String>().unknown(null, "未知错误");
        else if (result == -4)
            return new SingleResponse<String>().error(null, 1001, "邮箱已被注册");
        return new SingleResponse<String>().success("注册成功!");
    }

    @PostMapping("/login")
    @ApiOperation(value = "/login", notes = "登录")
    public SingleResponse<UserWithTokenDto> login(@RequestBody RegisterAndLoginParam loginParam) throws Exception {
        UserWithTokenDto userWithTokenDto = userService.login(loginParam);
        long result = userWithTokenDto.getId();
        if (result == -1)
            return new SingleResponse<UserWithTokenDto>().error(null, 1008, "登录邮箱或密码格式错误");
        else if (result == -2)
            return new SingleResponse<UserWithTokenDto>().error(null, 1009, "登录邮箱未注册");
        else if (result == -3)
            return new SingleResponse<UserWithTokenDto>().error(null, 1004, "登录密码错误");
        return new SingleResponse<UserWithTokenDto>().success(userWithTokenDto);
    }

    @PostMapping("/logout")
    @ApiOperation(value = "/logout", notes = "退出登录")
    public SingleResponse<String> logout() {
        //参数是@RequestHeader(value="USERNAME")String username
        //返回值String为""
        return null;
    }

    @PostMapping("/get/info/{userId}")
    @ApiOperation(value = "/get/info/{userId}", notes = "获取用户相关信息")
    public SingleResponse<UserDto> getUserInfo(@PathVariable("userId") Long userId) {
        UserDto userDto = userService.getUserDtoById(userId);
        if (userDto == null) {
            return new SingleResponse<UserDto>().error(null, 1007, "该用户不存在");
        }
        return new SingleResponse<UserDto>().success(userDto);
    }

    @PutMapping("/update/username")
    @ApiOperation(value = "/update/username", notes = "更新用户名")
    public SingleResponse<UserDto> updateUsername(@RequestBody UsernameParam usernameParam,
                                                  @RequestHeader(USER_ID_HEADER) Long userId) {
        int result = userService.updateUsername(usernameParam.getUsername(), userId);
        if (result == -1)
            return new SingleResponse<UserDto>().error(null, 1007, "该用户不存在");
        else if (result == 0)
            return new SingleResponse<UserDto>().unknown(null, "未知错误");
        else if (result == -2)
            return new SingleResponse<UserDto>().error(null, 1003, "用户名已重复");
        UserDto userDto = userService.getUserDtoById(userId);
        return new SingleResponse<UserDto>().success(userDto);
    }

    @PutMapping("/update/introduce")
    @ApiOperation(value = "/update/introduce", notes = "更新个性签名")
    public SingleResponse<UserDto> updateIntroduce(@RequestBody IntroduceParam introduceParam,
                                                   @RequestHeader(USER_ID_HEADER) Long userId) {
        int result = userService.updateIntroduce(introduceParam.getIntroduce(), userId);
        if (result == -1)
            return new SingleResponse<UserDto>().error(null, 1007, "该用户不存在");
        else if (result == 0)
            return new SingleResponse<UserDto>().unknown(null, "未知错误");
        UserDto userDto = userService.getUserDtoById(userId);
        return new SingleResponse<UserDto>().success(userDto);
    }

    @PutMapping("/update/avatar")
    @ApiOperation(value = "/update/avatar", notes = "更新头像地址")
    public SingleResponse<UserDto> updateAvatar(@RequestBody AvatarParam avatarParam,
                                                @RequestHeader(USER_ID_HEADER) Long userId) {
        int result = userService.updateAvatar(avatarParam.getAvatar(), userId);
        if (result == -1)
            return new SingleResponse<UserDto>().error(null, 1007, "该用户不存在");
        else if (result == 0)
            return new SingleResponse<UserDto>().unknown(null, "未知错误");
        UserDto userDto = userService.getUserDtoById(userId);
        return new SingleResponse<UserDto>().success(userDto);
    }

    @PutMapping("/update/cover")
    @ApiOperation(value = "/update/cover", notes = "更新用户中心封面地址")
    public SingleResponse<UserDto> updateCover(@RequestBody CoverParam coverParam,
                                               @RequestHeader(USER_ID_HEADER) Long userId) {
        int result = userService.updateCover(coverParam.getCover(), userId);
        if (result == -1)
            return new SingleResponse<UserDto>().error(null, 1007, "该用户不存在");
        else if (result == 0)
            return new SingleResponse<UserDto>().unknown(null, "未知错误");
        UserDto userDto = userService.getUserDtoById(userId);
        return new SingleResponse<UserDto>().success(userDto);
    }

    @PutMapping("/update/password")
    @ApiOperation(value = "/update/password", notes = "修改密码")
    public SingleResponse<UserDto> updatePassword(@RequestBody PasswordParam passwordParam,
                                                  @RequestHeader(USER_ID_HEADER) Long userId) throws Exception {
        int result = userService.updatePassword(passwordParam, userId);
        if (result == -1)
            return new SingleResponse<UserDto>().error(null, 1007, "该用户不存在");
        else if (result == 0)
            return new SingleResponse<UserDto>().unknown(null, "未知错误");
        else if (result == -2)
            return new SingleResponse<UserDto>().error(null, 1010, "旧密码错误");
        else if (result == -3)
            return new SingleResponse<UserDto>().error(null, 1006, "新密码格式错误");
        UserDto userDto = userService.getUserDtoById(userId);
        return new SingleResponse<UserDto>().success(userDto);
    }

    @PostMapping("/retrieve/password")
    @ApiOperation(value = "/retrieve/password", notes = "找回密码")
    public SingleResponse<RetrievePasswordDto> retrievePassword(@RequestBody EmailParam emailParam) {
        //客户端在点击“找回密码”后进入新的界面A，在界面输入email,然后发送请求
        RetrievePasswordDto retrievePasswordDto = userService.retrievePassword(emailParam.getEmail());
        if (retrievePasswordDto == null) {
            return new SingleResponse<RetrievePasswordDto>().error(null, 1007, "该用户不存在");
        }
        return new SingleResponse<RetrievePasswordDto>().success(retrievePasswordDto);
    }

    @PostMapping("/verify/password")
    @ApiOperation(value = "/verify/password", notes = "判断验证码是否正确")
    public SingleResponse<String> verifyCorrectness(@RequestBody VerificationCodeParam verificationCodeParam) throws Exception {
        /*
         * 客户端在新界面A输入email再次点击“找回密码”后进入新的界面B
         * 在新的界面B中填写新密码，确认密码，填写验证码
         * 点击“确认按钮”后发送“/verify”请求向后端发送以上数据以及token串
         * */
        int result = userService.verifyCorrectness(verificationCodeParam);
        if (result == -1)
            return new SingleResponse<String>().error(null, 1011, "验证码错误");
        else if (result == -2)
            return new SingleResponse<String>().error(null, 1006, "新密码格式错误");
        else if (result == 0)
            return new SingleResponse<String>().unknown(null, "未知错误");
        return new SingleResponse<String>().success("密码修改成功！");
    }

    @GetMapping("/get/avatarAndUsername")
    @ApiOperation(value = "/get/avatarAndUsername", notes = "获取用户相关信息")
    public SingleResponse<UserAvatarAndUsernameDto> getAvatarAndUsername(long id) {
        UserAvatarAndUsernameDto dto = userService.getAvatarAndUsername(id);
        if (dto == null)
            return new SingleResponse<UserAvatarAndUsernameDto>().unknown(null, "未知错误");
        return new SingleResponse<UserAvatarAndUsernameDto>().success(dto);
    }

    @PostMapping("/follow/{userId}")
    @ApiOperation(value = "/follow/{userId}", notes = "关注用户")
    public SingleResponse<Boolean> followUser(@PathVariable("userId") Long followUserId,
                                              @RequestHeader(USER_ID_HEADER) Long userId) {
        int result = userService.followUser(userId, followUserId);
        if (result == -1)
            return new SingleResponse<Boolean>().error(false, 1007, "用户不存在");
        else if (result == -2)
            return new SingleResponse<Boolean>().error(false, 1008, "已经关注");
        else if (result == 0)
            return new SingleResponse<Boolean>().unknown(false, "未知错误");
        return new SingleResponse<Boolean>().success(true);
    }

    @DeleteMapping("/unfollow/{userId}")
    @ApiOperation(value = "/unfollow/{userId}", notes = "取消关注用户")
    public SingleResponse<Boolean> unfollowUser(@PathVariable("userId") Long followUserId,
                                                @RequestHeader(USER_ID_HEADER) Long userId) {
        int result = userService.unfollowUser(userId, followUserId);
        if (result == -1)
            return new SingleResponse<Boolean>().error(false, 1007, "用户不存在");
        else if (result == -2)
            return new SingleResponse<Boolean>().error(false, 1008, "已经取消关注");
        else if (result == 0)
            return new SingleResponse<Boolean>().unknown(false, "未知错误");
        return new SingleResponse<Boolean>().success(true);
    }

    @GetMapping("/isFollow/{userId}")
    @ApiOperation(value = "/isFollow/{userId}", notes = "判断是否关注用户")
    public SingleResponse<Boolean> isFollowUser(@PathVariable("userId") Long followUserId,
                                                @RequestHeader(USER_ID_HEADER) Long userId) {
        int result = userService.isFollowUser(userId, followUserId);
        if (result == -1)
            return new SingleResponse<Boolean>().error(false, 1007, "用户不存在");
        else if (result == 0)
            return new SingleResponse<Boolean>().unknown(false, "未知错误");
        return new SingleResponse<Boolean>().success(true);
    }

    @GetMapping("/list/following")
    @ApiOperation(value = "/list/following", notes = "获取关注的用户列表")
    public MultiResponse<UserDto> getFollowingList(@RequestHeader(USER_ID_HEADER) Long userId) {
        List<UserDto> dto = userService.getFollowingList(userId);
        if (dto == null)
            return new MultiResponse<UserDto>().unknown(null, "未知错误");
        return new MultiResponse<UserDto>().success(dto);
    }
}
