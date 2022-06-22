package com.scut.forum.util;

import com.scut.common.constant.RedisConstant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HotIndexUtilTest {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void test1(){

//        for(long id=1;id<=25;id++)
//            redisTemplate.opsForZSet().remove(RedisConstant.REDIS_ZSET_HOT_INDEX, id);
//
//        redisTemplate.opsForZSet().removeRange(RedisConstant.REDIS_ZSET_HOT_INDEX, 0, -1);
//
//        redisTemplate.opsForZSet().add(RedisConstant.REDIS_ZSET_HOT_INDEX, 32L, 0.0);

        Set<Long> range = redisTemplate.opsForZSet().reverseRange(RedisConstant.REDIS_ZSET_HOT_INDEX, 0, -1);
        for (Long o : range) {
            System.out.println(o);
            System.out.println(redisTemplate.opsForZSet().score(RedisConstant.REDIS_ZSET_HOT_INDEX,o));
        }

//        redisTemplate.opsForZSet().add(RedisConstant.REDIS_ZSET_HOT_INDEX, 21L, 0);


}

}