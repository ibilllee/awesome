package com.scut.user.service;

import com.scut.common.dto.request.CreateTokenParam;
import com.scut.common.dto.request.RegisterAndLoginParam;
import com.scut.common.dto.response.TokenDto;
import com.scut.common.utils.JwtUtil;
import com.scut.common.utils.ValidityCheckUtil;
import com.scut.user.entity.User;
import com.scut.user.mapper.UserMapper;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    @Transactional
    public TokenDto login(RegisterAndLoginParam loginParam){
        //!!!!!数据库中的密码需要用md5进行加密
        //获取用户输入的邮箱、密码
        String loginEmail = loginParam.getEmail();
        String loginPassword = loginParam.getPassword();
        //根据用户输入的邮箱获取数据库中的user信息数据
        User user = userMapper.selectAllByEmail(loginEmail);
        //获得用户输入的密码后，需要将其用Md5进行加密，然后再将其与数据库中的密码进行比较
        if(ValidityCheckUtil.isValidEmail(loginEmail) && ValidityCheckUtil.isValidPassword(loginPassword) && user != null){
            if (loginEmail.equals(user.getEmail()) && loginPassword.equals(user.getPassword())){
                TokenDto tokenDto = new TokenDto();
                CreateTokenParam createTokenParam = new CreateTokenParam();
                createTokenParam.setEmail(user.getEmail());
                createTokenParam.setId(user.getId());
                createTokenParam.setUsername(user.getUsername());
                tokenDto.setToken(JwtUtil.createToken(createTokenParam));
                tokenDto.setId(user.getId());
                tokenDto.setEmail(user.getEmail());
                tokenDto.setUsername(user.getUsername());
                tokenDto.setIntroduce(user.getIntroduce());
                tokenDto.setAvatar(user.getAvatar());
                tokenDto.setCover(user.getCover());
                return tokenDto;
            }
        }
        return null;
    }

}
