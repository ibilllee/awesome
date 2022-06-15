package com.scut.forum.util;

import com.scut.common.constant.RedisConstant;
import com.scut.forum.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HotIndexUtil {
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redis;

    public static RedisTemplate redisTemplate;

    @PostConstruct
    private void getRedisTemplate() {
        redisTemplate = this.redis;
    }

    private static double getHotIndex(Article article) {
        long favorCount = article.getFavorCount();
        long likeCount = article.getLikeCount();
        long viewCount = article.getViewCount();

        double durationMills = System.currentTimeMillis() - article.getCreateTime();

        double totalA = favorCount * 8 + likeCount * 3 + viewCount;
        double totalB = durationMills / 1000 / 60 / 60 + 2.0;

        return totalA / Math.pow(totalB, 2.0);
    }

    public static void updateArticleHotIndex(Article article) {
        double hotIndex = getHotIndex(article);
        redisTemplate.opsForZSet().remove(RedisConstant.REDIS_ZSET_HOT_INDEX, article.getId());
        redisTemplate.opsForZSet().add(RedisConstant.REDIS_ZSET_HOT_INDEX, article.getId(), hotIndex);
        System.out.println(redisTemplate.opsForZSet().score(RedisConstant.REDIS_ZSET_HOT_INDEX, article.getId()));
    }

}
