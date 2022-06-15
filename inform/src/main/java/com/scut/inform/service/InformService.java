package com.scut.inform.service;

//import org.apache.rocketmq.spring.core.RocketMQTemplate;

import com.scut.common.constant.RedisConstant;
import com.scut.common.dto.response.InformDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class InformService {
    @Autowired
    private RedisTemplate redisTemplate;

    public void addInformToRedis(InformDto message) {
        String setName = RedisConstant.REDIS_SET_INFORM + "$" + message.getUserId();
        redisTemplate.opsForSet().add(setName, message);
    }

    public Set<InformDto> getInformsByUserId(long userId) {
        Set<InformDto> members = redisTemplate.opsForSet().members(RedisConstant.REDIS_SET_INFORM + "$" + userId);
        redisTemplate.delete(RedisConstant.REDIS_SET_INFORM + "$" + userId);
        return members;
    }
}
