package com.scut.forum.feign;

import com.scut.common.dto.response.UserAvatarAndUsernameDto;
import com.scut.common.response.SingleResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserFeignServiceTest {
    @Resource
    private UserFeignService userFeignService;
    @Test
    public void test1(){
        SingleResponse<UserAvatarAndUsernameDto> avatarAndUsername = userFeignService.getAvatarAndUsername(100);
        System.out.println(avatarAndUsername.getData());
    }

}