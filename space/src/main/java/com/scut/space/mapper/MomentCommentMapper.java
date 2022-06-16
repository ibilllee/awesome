package com.scut.space.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scut.space.entity.MomentComment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MomentCommentMapper extends BaseMapper<MomentComment> {

    @Update(" UPDATE moment_comment SET reply_count = reply_count + #{value} WHERE id = #{id} ")
    void updateReplyCount(@Param("id") long id, @Param("value") int value);

    @Delete(" DELETE FROM moment_comment WHERE reply_id = #{id} ")
    int deleteByParentId(long id);
}
