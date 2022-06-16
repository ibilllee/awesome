package com.scut.user.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.scut.common.constant.MQConstant;
import com.scut.common.dto.request.*;
import com.scut.common.dto.response.*;
import com.scut.common.utils.JwtUtil;
import com.scut.common.utils.MD5Util;
import com.scut.common.utils.ValidityCheckUtil;
import com.scut.user.entity.User;
import com.scut.user.entity.UserFollow;
import com.scut.user.mapper.UserFollowMapper;
import com.scut.user.mapper.UserMapper;
import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserFollowMapper userFollowMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private HttpServletRequest request;

    @Transactional
    public int register(RegisterAndLoginParam registerParam) throws Exception {
        String registerEmail = registerParam.getEmail();
        String registerPassword = registerParam.getPassword();
        if (ValidityCheckUtil.isValidEmail(registerEmail)) {
            List<String> emails = userMapper.selectAllEmail();
            for (String email : emails) {
                if (registerEmail.equals(email)) {
                    return -4;//邮箱已被注册
                }
            }
            if (ValidityCheckUtil.isValidPassword(registerPassword)) {
                User user = new User(registerParam);
                if (userMapper.insert(user) == 1) {
                    return 1;
                } else {
                    return -3;//未知错误
                }
            } else {
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
        UserWithTokenDto userWithTokenDto = new UserWithTokenDto();
        if (ValidityCheckUtil.isValidEmail(loginEmail) && ValidityCheckUtil.isValidPassword(loginPassword)) {
            if (user != null) {
                //验证密码是否正确
                //获得用户输入的密码后，需要将其用Md5进行加密，然后再将其与数据库中的密码进行比较
                if (MD5Util.verify(loginPassword, user.getPassword())) {
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

                    ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
                    valueOperations.set("token", token);
                } else {
                    userWithTokenDto.setId(-3);//登录密码错误
                }
            } else {
                userWithTokenDto.setId(-2);//邮箱未注册
            }
        } else {
            userWithTokenDto.setId(-1);//邮箱、密码不符合格式
        }
        return userWithTokenDto;
    }

    @Transactional
    public int updateUsername(String username, Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) return -1;//用户不存在
        List<String> usernames = userMapper.selectAllUsername();
        for (String name : usernames) {
            if (username.equals(name)) {
                return -2;//用户名已重复
            }
        }
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(User::getId, userId).set(User::getUsername, username);
        Integer rows = userMapper.update(null, lambdaUpdateWrapper);
        return rows;
    }

    @Transactional
    public int updateIntroduce(String introduce, Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) return -1;//用户不存在
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(User::getId, userId).set(User::getIntroduce, introduce);
        Integer rows = userMapper.update(null, lambdaUpdateWrapper);
        return rows;
    }

    @Transactional
    public int updateAvatar(String avatar, Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) return -1;//用户不存在
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(User::getId, userId).set(User::getAvatar, avatar);
        Integer rows = userMapper.update(null, lambdaUpdateWrapper);
        return rows;
    }

    @Transactional
    public int updateCover(String cover, Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) return -1;//用户不存在
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(User::getId, userId).set(User::getCover, cover);
        Integer rows = userMapper.update(null, lambdaUpdateWrapper);
        return rows;
    }

    @Transactional
    public int updatePassword(PasswordParam passwordParam, Long userId) throws Exception {
        User user = userMapper.selectById(userId);
        if (user == null) return -1;//用户不存在
        String inputOldPassword = passwordParam.getOldPassword();
        String inputNewPassword = passwordParam.getNewPassword();
        if (MD5Util.verify(inputOldPassword, user.getPassword())) {
            //旧密码与数据库中的密码相同
            if (ValidityCheckUtil.isValidPassword(inputNewPassword)) {
                //新密码格式正确
                LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                String encryptionNewPassword = MD5Util.convertMD5(inputNewPassword);
                lambdaUpdateWrapper.eq(User::getId, userId).set(User::getPassword, encryptionNewPassword);
                Integer rows = userMapper.update(null, lambdaUpdateWrapper);
                return rows;
            } else {
                return -3;//修改密码格式错误：1006
            }
        }
        return -2;//修改密码所填旧密码错误：1010
    }

    @Transactional
    public UserDto getUserDtoById(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) return null;//用户不存在
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setAvatar(user.getAvatar());
        userDto.setCover(user.getCover());
        userDto.setIntroduce(user.getIntroduce());
        return userDto;
    }

    @Transactional
    public RetrievePasswordDto retrievePassword(String email) {
        User user = userMapper.selectAllByEmail(email);
        if (user == null) return null;//用户不存在
        //随机生成由6位数字组成的验证码A
        int securityCode = (int) ((Math.random() * 9 + 1) * Math.pow(10, 5));
        //生成tokenB
        CreateTokenParam createTokenParam = new CreateTokenParam();
        createTokenParam.setId(user.getId());
        String securityToken = JwtUtil.createToken(createTokenParam);
        //存入redis：(key,value)=(B,A)
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(securityToken, String.valueOf(securityCode));
        //发送邮件,邮件里面包含了验证码：A
        EmailDto emailDto = new EmailDto(email, securityCode);
        rocketMQTemplate.convertAndSend(MQConstant.TOPIC_EMAIL, JSON.toJSONBytes(emailDto));
        //返回值包含：1、发送邮件的目的邮箱。2、token串
        RetrievePasswordDto retrievePasswordDto = new RetrievePasswordDto(email, securityToken);
        return retrievePasswordDto;
    }

    @Transactional
    public int verifyCorrectness(VerificationCodeParam verificationCodeParam) throws Exception {
        //获取传入的验证码为C,tokenB
        String securityCodeC = verificationCodeParam.getVerificationCode();
        String securityToken = verificationCodeParam.getToken();
        //通过token B,从Redis中获取验证码A
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        String securityCodeA = valueOperations.get(securityToken);
        //判断A和C是否相等
        if (securityCodeC != null && securityCodeA != null) {
            if (securityCodeA.equals(securityCodeC)) {
                //若相等，转发请求到/update/password，返回的String提示用户成功
                String inputNewPassword = verificationCodeParam.getPassword();
                String targetEmail = verificationCodeParam.getEmail();
                if (ValidityCheckUtil.isValidPassword(inputNewPassword)) {
                    //新密码格式正确
                    LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                    String encryptionNewPassword = MD5Util.convertMD5(inputNewPassword);
                    lambdaUpdateWrapper.eq(User::getEmail, targetEmail).set(User::getPassword, encryptionNewPassword);
                    Integer rows = userMapper.update(null, lambdaUpdateWrapper);
                    return rows;
                } else {
                    return -2;//新密码格式错误：1006
                }
            } else {
                return -1;//验证码错误
            }
        }
        return -1;//验证码为空或验证码错误
    }

    public UserAvatarAndUsernameDto getAvatarAndUsername(long id) {
        Map<Object, String> map = userMapper.selectAvatarAndUsernameById(id);
        return new UserAvatarAndUsernameDto(map.get("avatar"), map.get("username"));
    }

    public int followUser(long userId, long followUserId) {
        User followUser = userMapper.selectById(followUserId);
        if (followUser == null) return -1;//用户不存在
        UserFollow userFollowResult = userFollowMapper.selectOne(
                new QueryWrapper<UserFollow>()
                        .eq("user_id", userId)
                        .eq("follow_user_id", followUserId));
        if (userFollowResult != null) return -2;//已关注
        UserFollow userFollow = new UserFollow(0L, userId, followUserId);
        return userFollowMapper.insert(userFollow) == 1 ? 1 : 0;
    }

    public int unfollowUser(long userId, long followUserId) {
        User followUser = userMapper.selectById(followUserId);
        if (followUser == null) return -1;//用户不存在

        UserFollow userFollow = userFollowMapper.selectOne(
                new QueryWrapper<UserFollow>()
                        .eq("user_id", userId)
                        .eq("follow_user_id", followUserId));
        if (userFollow == null) return -2;//未关注

        return userFollowMapper.deleteById(userFollow.getId()) == 1 ? 1 : 0;
    }

    public int isFollowUser(long userId, long followUserId) {
        User followUser = userMapper.selectById(followUserId);
        if (followUser == null) return -1;//用户不存在
        return userFollowMapper.selectOne(
                new QueryWrapper<UserFollow>()
                        .eq("user_id", userId)
                        .eq("follow_user_id", followUserId)) != null ? 1 : 0;
    }

    public List<UserDto> getFollowingList(Long userId) {
        List<User> users = userMapper.selectList(new QueryWrapper<User>().
                exists(" SELECT 1 FROM user_follow " +
                        " WHERE follow_user_id = user.id " +
                        " AND user_id = " + userId));
        ArrayList<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(user.getDto());
        }
        return userDtos;
    }
}
