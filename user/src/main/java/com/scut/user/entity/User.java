package com.scut.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.scut.common.dto.request.RegisterAndLoginParam;
import com.scut.common.utils.MD5Util;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String email;
    private String username;
    private String password;
    private String avatar;
    private String cover;
    private String introduce;
    private Long createTime;

    public User(RegisterAndLoginParam registerParam) throws Exception {
        email = registerParam.getEmail();
        password = MD5Util.convertMD5(registerParam.getPassword());
        username = UUID.randomUUID().toString();
        avatar = "";
        cover = "";
        introduce = "";
        createTime = System.currentTimeMillis();
    }
}
