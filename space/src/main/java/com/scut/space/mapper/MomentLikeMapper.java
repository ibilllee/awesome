package com.scut.space.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scut.space.entity.MomentLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MomentLikeMapper extends BaseMapper<MomentLike> {

    @Update(" UPDATE moment SET like_count = like_count + #{value} WHERE id = #{id} ")
    void updateLikeCount(@Param("id") long id, @Param("value") int value);
}
