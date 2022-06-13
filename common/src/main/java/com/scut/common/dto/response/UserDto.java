package com.scut.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    long id;
    String email;
    String username;
    String password;
    String avatar;
    String cover;
    String introduce;
    long createTime;
}
