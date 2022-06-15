package com.scut.board.feign;

import com.scut.common.dto.response.UserAvatarAndUsernameDto;
import com.scut.common.response.SingleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "user-service", path = "user-service/user")
public interface UserFeignService {
    @GetMapping("/get/avatarAndUsername")
    SingleResponse<UserAvatarAndUsernameDto> getAvatarAndUsername(@RequestParam("id") long id);
}
