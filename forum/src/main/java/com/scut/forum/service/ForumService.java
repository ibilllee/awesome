package com.scut.forum.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scut.common.dto.request.ForumListParam;
import com.scut.common.dto.request.ForumParam;
import com.scut.common.dto.request.ForumTagParam;
import com.scut.common.dto.response.ForumDto;
import com.scut.common.dto.response.ForumTagDto;
import com.scut.forum.entity.Forum;
import com.scut.forum.entity.ForumFavor;
import com.scut.forum.entity.ForumTag;
import com.scut.forum.mapper.ForumFavorMapper;
import com.scut.forum.mapper.ForumMapper;
import com.scut.forum.mapper.ForumTagMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ForumService {
    @Resource
    private ForumMapper forumMapper;
    @Resource
    private ForumTagMapper forumTagMapper;
    @Resource
    private ForumFavorMapper forumFavorMapper;

    @Transactional
    public ForumDto submit(ForumParam forumParam) {
        Forum forum = new Forum(0L, forumParam.getGameId(), 0L, 0L);
        if (forumMapper.insert(forum) == 1) {
            Map<Object, String> objects = forumMapper.selectCoverAndLogoAndNameByGameId(forumParam.getGameId());
            String cover = objects.get("cover");
            String logo = objects.get("logo");
            String name = objects.get("name");
            return new ForumDto(forum.getId(), forum.getGameId(), 0, 0, cover, logo, name);
        }
        return null;
    }

    public ForumDto get(long id) {
        Forum forum = forumMapper.selectById(id);
        if (forum != null) {
            Map<Object, String> objects = forumMapper.selectCoverAndLogoAndNameByGameId(forum.getGameId());
            String cover = objects.get("cover");
            String logo = objects.get("logo");
            String name = objects.get("name");
            return new ForumDto(forum.getId(), forum.getGameId(), 0, 0, cover, logo, name);
        }
        return null;
    }

    public List<ForumDto> getList(ForumListParam forumListParam) {
        Page<Forum> forumPage = new Page<>(forumListParam.getPage(), forumListParam.getSize());
        Page<Forum> page = new LambdaQueryChainWrapper<>(forumMapper).page(forumPage);
        List<Forum> forums = page.getRecords();
        return forumsToForumDtos(forums);
    }

    public List<ForumDto> getFavorList(ForumListParam forumListParam, long userId) {
        Page<Forum> forumPage = new Page<>(forumListParam.getPage(), forumListParam.getSize());
        Page<Forum> page = new LambdaQueryChainWrapper<>(forumMapper)
                .exists(" SELECT 1 FROM forum_favor WHERE forum.id = forum_id AND " + userId + " = user_id")
                .page(forumPage);
        List<Forum> forums = page.getRecords();
        return forumsToForumDtos(forums);
    }

    public List<ForumDto> search(ForumListParam forumListParam) {
        String searchText = forumListParam.getSearchText();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < searchText.length(); i++) {
            stringBuilder.append("%").append(searchText.charAt(i));
        }
        stringBuilder.append("%");
        String sql = " SELECT * FROM forum WHERE game_id IN ( SELECT id FROM game WHERE name LIKE '" + stringBuilder.toString() + "' ) ";
        List<Forum> forums = forumMapper.executeListQuery(sql);
        return forumsToForumDtos(forums);
    }

    private List<ForumDto> forumsToForumDtos(List<Forum> forums) {
        List<ForumDto> forumDtos = new ArrayList<>();
        for (Forum forum : forums) {
            Map<Object, String> objects = forumMapper.selectCoverAndLogoAndNameByGameId(forum.getGameId());
            String cover = objects.get("cover");
            String logo = objects.get("logo");
            String name = objects.get("name");
            forumDtos.add(new ForumDto(forum.getId(), forum.getGameId(), 0, 0, cover, logo, name));
        }
        return forumDtos;
    }

    public int delete(long id) {
        return forumMapper.deleteById(id);
    }

    public ForumTagDto submitTag(ForumTagParam forumTagParam) {
        if (forumTagMapper.isExist(forumTagParam.getContent()) != null) {
            return null;
        }
        ForumTag forumTag = new ForumTag(0L, forumTagParam.getForumId(), forumTagParam.getContent());
        if (forumTagMapper.insert(forumTag) == 1) {
            return new ForumTagDto(forumTag.getId(), forumTag.getForumId(), forumTag.getContent());
        }
        return null;
    }

    public List<ForumTagDto> getTagList(long id) {
        QueryWrapper<ForumTag> wrapper = new QueryWrapper<>();
        wrapper.select("id", "content").eq("forum_id", id);
        List<ForumTag> forumTags = forumTagMapper.selectList(wrapper);
        if (forumTags == null) return null;
        List<ForumTagDto> tagList = new ArrayList<>();
        for (ForumTag forumTag : forumTags) {
            tagList.add(new ForumTagDto(forumTag.getId(), id, forumTag.getContent()));
        }
        return tagList;
    }

    public int deleteTag(long id) {
        return forumTagMapper.deleteById(id);
    }

    @Transactional
    public int favor(long id, long userId) {
        if (forumMapper.selectById(id) == null) return -1;
        ForumFavor forumFavor = forumFavorMapper.selectOne(new QueryWrapper<ForumFavor>()
                .eq("forum_id", id).eq("user_id", userId));
        if (forumFavor == null) {
            forumFavor = new ForumFavor(0L, userId, id);
            if (forumFavorMapper.insert(forumFavor) == 1) {
                forumMapper.updateFavorCount(id, 1);
                return 1;
            } else {
                return 0;
            }
        } else {
            return -2;
        }
    }

    @Transactional
    public int unfavor(long id, long userId) {
        if (forumMapper.selectById(id) == null) return -1;
        ForumFavor forumFavor = forumFavorMapper.selectOne(new QueryWrapper<ForumFavor>()
                .eq("forum_id", id).eq("user_id", userId));
        if (forumFavor == null) {
            return -2;
        } else {
            if (forumFavorMapper.delete(new QueryWrapper<ForumFavor>()
                    .eq("forum_id", id).eq("user_id", userId)) == 1) {
                forumMapper.updateFavorCount(id, -1);
                return 1;
            } else {
                return 0;
            }
        }
    }

    public boolean isFavor(long userId, long id) {
        return forumFavorMapper.selectOne(new QueryWrapper<ForumFavor>()
                .eq("forum_id", id).eq("user_id", userId)) != null;
    }
}
