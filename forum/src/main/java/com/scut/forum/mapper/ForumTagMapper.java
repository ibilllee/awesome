package com.scut.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scut.forum.entity.ForumTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ForumTagMapper extends BaseMapper<ForumTag> {
    @Select(" SELECT 1 FROM forum_tag WHERE content = #{content} ")
    Boolean isExist(@Param("content") String content);
}
