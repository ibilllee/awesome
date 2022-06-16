package com.scut.user.mapper;

import com.scut.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class UserMapperTest {
    @Resource
    private UserMapper userMapper;
    @Test
    public void test(){
//        User user=new User(0,"123@qq.com","admin","123456","1","1","1",0);
//        userMapper.insert(user);
        Map<String,Object> map = new HashMap<>();
        map.put("email","123@qq.com");
        userMapper.selectByMap(map);

    }
}
