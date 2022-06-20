package com.scut.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scut.forum.entity.Forum;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface ForumMapper extends BaseMapper<Forum> {
    @Select(" SELECT cover, logo, name FROM game WHERE id = #{gameId} ")
    Map<Object, String> selectCoverAndLogoAndNameByGameId(@Param("gameId") long gameId);

    @Select({"${sql}"})
    @ResultType(Forum.class)
    List<Forum> executeListQuery(@Param("sql") String sql);

    @Update(" UPDATE forum SET favor_count = favor_count + #{value} WHERE id = #{id} ")
    void updateFavorCount(@Param("id") long id, @Param("value") int value);

    @Update(" UPDATE forum SET article_count = article_count + #{value} WHERE id = #{id} ")
    void updateArticleCount(@Param("id") long id, @Param("value") int value);
}
