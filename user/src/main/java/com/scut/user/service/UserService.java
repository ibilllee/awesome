package com.scut.user.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.scut.common.dto.request.CreateTokenParam;
import com.scut.common.dto.request.PasswordParam;
import com.scut.common.dto.request.RegisterAndLoginParam;
import com.scut.common.dto.request.UsernameParam;
import com.scut.common.dto.response.UserDto;
import com.scut.common.dto.response.UserWithTokenDto;
import com.scut.common.dto.response.UserAvatarAndUsernameDto;
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
import java.util.Map;
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
                    createTokenParam.setId(user.getId());

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

    @Transactional
    public int updateUsername(String username,Long userId){
        User user = userMapper.selectById(userId);
        if(user == null)return -1;//用户不存在
        List<String> usernames = userMapper.selectAllUsername();
        for(String name : usernames){
            if (username.equals(name)){
                return -2;//用户名已重复
            }
        }
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(User::getId, userId).set(User::getUsername,username);
        Integer rows = userMapper.update(null, lambdaUpdateWrapper);
        return rows;
    }

    @Transactional
    public int updateIntroduce(String introduce,Long userId){
        User user = userMapper.selectById(userId);
        if(user == null)return -1;//用户不存在
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(User::getId, userId).set(User::getIntroduce,introduce);
        Integer rows = userMapper.update(null, lambdaUpdateWrapper);
        return rows;
    }

    @Transactional
    public int updateAvatar(String avatar,Long userId){
        User user = userMapper.selectById(userId);
        if(user == null)return -1;//用户不存在
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(User::getId, userId).set(User::getAvatar,avatar);
        Integer rows = userMapper.update(null, lambdaUpdateWrapper);
        return rows;
    }

    @Transactional
    public int updateCover(String cover,Long userId){
        User user = userMapper.selectById(userId);
        if(user == null)return -1;//用户不存在
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(User::getId, userId).set(User::getCover,cover);
        Integer rows = userMapper.update(null, lambdaUpdateWrapper);
        return rows;
    }

    @Transactional
    public int updatePassword(PasswordParam passwordParam, Long userId) throws Exception {
        User user = userMapper.selectById(userId);
        if(user == null)return -1;//用户不存在
        String inputOldPassword = passwordParam.getOldPassword();
        String inputNewPassword = passwordParam.getNewPassword();
        if (MD5Util.verify(inputOldPassword,user.getPassword())){
            //旧密码与数据库中的密码相同
            if(ValidityCheckUtil.isValidPassword(inputNewPassword)){
                //新密码格式正确
                LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                String encryptionNewPassword = MD5Util.convertMD5(inputNewPassword);
                lambdaUpdateWrapper.eq(User::getId, userId).set(User::getPassword,encryptionNewPassword);
                Integer rows = userMapper.update(null, lambdaUpdateWrapper);
                return rows;
            }else{
                return -3;//修改密码格式错误：1006
            }
        }
        return -2;//修改密码所填旧密码错误：1010
    }

    @Transactional
    public UserDto getUserDtoById(Long userId){
        User user = userMapper.selectById(userId);
        if(user == null)return null;//用户不存在
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setAvatar(user.getAvatar());
        userDto.setCover(user.getCover());
        userDto.setIntroduce(user.getIntroduce());
        return userDto;
    }


    public UserAvatarAndUsernameDto getAvatarAndUsername(long id) {
        Map<Object, String> map = userMapper.selectAvatarAndUsernameById(id);
        return new UserAvatarAndUsernameDto(map.get("avatar"), map.get("username"));
    }
}
