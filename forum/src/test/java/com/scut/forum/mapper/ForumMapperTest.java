package com.scut.forum.mapper;

import com.scut.forum.entity.Forum;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ForumMapperTest {
    @Resource
    private ForumMapper forumMapper;

    @Test
    public void test(){
        Forum forum = new Forum(0, 1, 0, 0);
        forumMapper.insert(forum);
    }

}