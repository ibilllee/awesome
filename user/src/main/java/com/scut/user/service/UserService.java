package com.scut.user.service;

import com.scut.common.dto.request.CreateTokenParam;
import com.scut.common.dto.request.RegisterAndLoginParam;
import com.scut.common.dto.response.UserWithTokenDto;
import com.scut.common.utils.JwtUtil;
import com.scut.common.utils.MD5Util;
import com.scut.common.utils.ValidityCheckUtil;
import com.scut.user.entity.User;
import com.scut.user.mapper.UserMapper;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Transactional
    public int register(RegisterAndLoginParam registerParam) throws Exception {
        String registerEmail = registerParam.getEmail();
        String registerPassword = registerParam.getPassword();
        if(ValidityCheckUtil.isValidEmail(registerEmail)){
            List<String> emails = userMapper.selectAllEmail();
            for(String email : emails){
                if (registerEmail.equals(email)){
                    return -4;//邮箱已被注册
                }
            }
            if(ValidityCheckUtil.isValidPassword(registerPassword)){
                User user = new User(registerParam);
                if(userMapper.insert(user) == 1){
                    return 1;
                }else{
                    return -3;//未知错误
                }
            }else{
                return -2;//注册密码不符合格式
            }
        }
        return -1;//注册邮箱不符合格式

    }



    @Transactional
    public UserWithTokenDto login(RegisterAndLoginParam loginParam) throws Exception {
        //!!!!!数据库中的密码需要用md5进行加密
        //获取用户输入的邮箱、密码
        String loginEmail = loginParam.getEmail();
        String loginPassword = loginParam.getPassword();
        //根据用户输入的邮箱获取数据库中的user信息数据
        User user = userMapper.selectAllByEmail(loginEmail);
        UserWithTokenDto userWithTokenDto =new UserWithTokenDto();
        if(ValidityCheckUtil.isValidEmail(loginEmail) && ValidityCheckUtil.isValidPassword(loginPassword)){
            if(user != null){
                //验证密码是否正确
                //获得用户输入的密码后，需要将其用Md5进行加密，然后再将其与数据库中的密码进行比较
                if (MD5Util.verify(loginPassword,user.getPassword())){
                    CreateTokenParam createTokenParam = new CreateTokenParam();
                    createTokenParam.setEmail(user.getEmail());
                    createTokenParam.setId(user.getId());
                    createTokenParam.setUsername(user.getUsername());

                    String token = JwtUtil.createToken(createTokenParam);
                    userWithTokenDto.setToken(token);
                    userWithTokenDto.setId(user.getId());
                    userWithTokenDto.setEmail(user.getEmail());
                    userWithTokenDto.setUsername(user.getUsername());
                    userWithTokenDto.setIntroduce(user.getIntroduce());
                    userWithTokenDto.setAvatar(user.getAvatar());
                    userWithTokenDto.setCover(user.getCover());

                    ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
                    valueOperations.set("token",token);
                }else{
                    userWithTokenDto.setId(-3);//登录密码错误
                }
            }else{
                userWithTokenDto.setId(-2);//邮箱未注册
            }
        }else{
            userWithTokenDto.setId(-1);//邮箱、密码不符合格式
        }
        return userWithTokenDto;
    }

}
