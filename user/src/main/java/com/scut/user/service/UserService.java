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
                    return -4;//??????????????????
                }
            }
            if (ValidityCheckUtil.isValidPassword(registerPassword)) {
                User user = new User(registerParam);
                if (userMapper.insert(user) == 1) {
                    return 1;
                } else {
                    return -3;//????????????
                }
            } else {
                return -2;//???????????????????????????
            }
        }
        return -1;//???????????????????????????

    }


    @Transactional
    public UserWithTokenDto login(RegisterAndLoginParam loginParam) throws Exception {
        //!!!!!??????????????????????????????md5????????????
        //????????????????????????????????????
        String loginEmail = loginParam.getEmail();
        String loginPassword = loginParam.getPassword();
        //????????????????????????????????????????????????user????????????
        User user = userMapper.selectAllByEmail(loginEmail);
        UserWithTokenDto userWithTokenDto = new UserWithTokenDto();
        if (ValidityCheckUtil.isValidEmail(loginEmail) && ValidityCheckUtil.isValidPassword(loginPassword)) {
            if (user != null) {
                //????????????????????????
                //????????????????????????????????????????????????Md5??????????????????????????????????????????????????????????????????
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
                    userWithTokenDto.setId(-3);//??????????????????
                }
            } else {
                userWithTokenDto.setId(-2);//???????????????
            }
        } else {
            userWithTokenDto.setId(-1);//??????????????????????????????
        }
        return userWithTokenDto;
    }

    @Transactional
    public int updateUsername(String username, Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) return -1;//???????????????
        List<String> usernames = userMapper.selectAllUsername();
        for (String name : usernames) {
            if (username.equals(name)) {
                return -2;//??????????????????
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
        if (user == null) return -1;//???????????????
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(User::getId, userId).set(User::getIntroduce, introduce);
        Integer rows = userMapper.update(null, lambdaUpdateWrapper);
        return rows;
    }

    @Transactional
    public int updateAvatar(String avatar, Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) return -1;//???????????????
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(User::getId, userId).set(User::getAvatar, avatar);
        Integer rows = userMapper.update(null, lambdaUpdateWrapper);
        return rows;
    }

    @Transactional
    public int updateCover(String cover, Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) return -1;//???????????????
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(User::getId, userId).set(User::getCover, cover);
        Integer rows = userMapper.update(null, lambdaUpdateWrapper);
        return rows;
    }

    @Transactional
    public int updatePassword(PasswordParam passwordParam, Long userId) throws Exception {
        User user = userMapper.selectById(userId);
        if (user == null) return -1;//???????????????
        String inputOldPassword = passwordParam.getOldPassword();
        String inputNewPassword = passwordParam.getNewPassword();
        if (MD5Util.verify(inputOldPassword, user.getPassword())) {
            //???????????????????????????????????????
            if (ValidityCheckUtil.isValidPassword(inputNewPassword)) {
                //?????????????????????
                LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                String encryptionNewPassword = MD5Util.convertMD5(inputNewPassword);
                lambdaUpdateWrapper.eq(User::getId, userId).set(User::getPassword, encryptionNewPassword);
                Integer rows = userMapper.update(null, lambdaUpdateWrapper);
                return rows;
            } else {
                return -3;//???????????????????????????1006
            }
        }
        return -2;//????????????????????????????????????1010
    }

    @Transactional
    public UserDto getUserDtoById(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) return null;//???????????????
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
        if (user == null) return null;//???????????????
        //???????????????6???????????????????????????A
        int securityCode = (int) ((Math.random() * 9 + 1) * Math.pow(10, 5));
        //??????tokenB
        CreateTokenParam createTokenParam = new CreateTokenParam();
        createTokenParam.setId(user.getId());
        String securityToken = JwtUtil.createToken(createTokenParam);
        //??????redis???(key,value)=(B,A)
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(securityToken, String.valueOf(securityCode));
        //????????????,?????????????????????????????????A
        EmailDto emailDto = new EmailDto(email, securityCode);
        rocketMQTemplate.convertAndSend(MQConstant.TOPIC_EMAIL, JSON.toJSONBytes(emailDto));
        //??????????????????1?????????????????????????????????2???token???
        RetrievePasswordDto retrievePasswordDto = new RetrievePasswordDto(email, securityToken);
        return retrievePasswordDto;
    }

    @Transactional
    public int verifyCorrectness(VerificationCodeParam verificationCodeParam) throws Exception {
        //???????????????????????????C,tokenB
        String securityCodeC = verificationCodeParam.getVerificationCode();
        String securityToken = verificationCodeParam.getToken();
        //??????token B,???Redis??????????????????A
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        String securityCodeA = valueOperations.get(securityToken);
        //??????A???C????????????
        if (securityCodeC != null && securityCodeA != null) {
            if (securityCodeA.equals(securityCodeC)) {
                //???????????????????????????/update/password????????????String??????????????????
                String inputNewPassword = verificationCodeParam.getPassword();
                String targetEmail = verificationCodeParam.getEmail();
                if (ValidityCheckUtil.isValidPassword(inputNewPassword)) {
                    //?????????????????????
                    LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                    String encryptionNewPassword = MD5Util.convertMD5(inputNewPassword);
                    lambdaUpdateWrapper.eq(User::getEmail, targetEmail).set(User::getPassword, encryptionNewPassword);
                    Integer rows = userMapper.update(null, lambdaUpdateWrapper);
                    return rows;
                } else {
                    return -2;//????????????????????????1006
                }
            } else {
                return -1;//???????????????
            }
        }
        return -1;//?????????????????????????????????
    }

    public UserAvatarAndUsernameDto getAvatarAndUsername(long id) {
        Map<Object, String> map = userMapper.selectAvatarAndUsernameById(id);
        return new UserAvatarAndUsernameDto(map.get("avatar"), map.get("username"));
    }

    public int followUser(long userId, long followUserId) {
        User followUser = userMapper.selectById(followUserId);
        if (followUser == null) return -1;//???????????????
        UserFollow userFollowResult = userFollowMapper.selectOne(
                new QueryWrapper<UserFollow>()
                        .eq("user_id", userId)
                        .eq("follow_user_id", followUserId));
        if (userFollowResult != null) return -2;//?????????
        UserFollow userFollow = new UserFollow(0L, userId, followUserId);
        return userFollowMapper.insert(userFollow) == 1 ? 1 : 0;
    }

    public int unfollowUser(long userId, long followUserId) {
        User followUser = userMapper.selectById(followUserId);
        if (followUser == null) return -1;//???????????????

        UserFollow userFollow = userFollowMapper.selectOne(
                new QueryWrapper<UserFollow>()
                        .eq("user_id", userId)
                        .eq("follow_user_id", followUserId));
        if (userFollow == null) return -2;//?????????

        return userFollowMapper.deleteById(userFollow.getId()) == 1 ? 1 : 0;
    }

    public int isFollowUser(long userId, long followUserId) {
        User followUser = userMapper.selectById(followUserId);
        if (followUser == null) return -1;//???????????????
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
