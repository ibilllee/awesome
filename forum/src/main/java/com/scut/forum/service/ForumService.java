package com.scut.forum.service;

import com.scut.common.dto.request.ForumParam;
import com.scut.common.dto.response.ForumDto;
import com.scut.forum.entity.Forum;
import com.scut.forum.mapper.ForumMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ForumService {
    @Resource
    private ForumMapper forumMapper;

    @Transactional
    public ForumDto submit(ForumParam forumParam) {
        Forum forum = new Forum(0, forumParam.getGameId(), 0, 0);
        if (forumMapper.insert(forum) == 1) {
            Map<Object,String> objects = forumMapper.selectCoverAndLogoByGameId(forumParam.getGameId());
            String cover = objects.get("cover");
            String logo = objects.get("logo");
            return new ForumDto(forum.getId(), forum.getGameId(), 0, 0, cover, logo);
        }
        return null;
    }
}
