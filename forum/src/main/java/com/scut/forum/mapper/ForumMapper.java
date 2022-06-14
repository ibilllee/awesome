package com.scut.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scut.forum.entity.Forum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface ForumMapper extends BaseMapper<Forum> {
    @Select(" SELECT cover, logo FROM game WHERE id = #{gameId} ")
    Map<Object,String> selectCoverAndLogoByGameId(long gameId);

    @Select({"${sql}"})
    @ResultType(Forum.class)
    List<Forum> executeListQuery(@Param("sql") String sql);
}
