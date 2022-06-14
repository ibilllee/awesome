package com.scut.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    String token;
    long id;
    String email;
    String username;
    String avatar;
    String cover;
    String introduce;
}
