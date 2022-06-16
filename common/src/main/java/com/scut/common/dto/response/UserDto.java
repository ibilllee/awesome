package com.scut.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    String email;
    String username;
    String avatar;
    String cover;
    String introduce;
}
