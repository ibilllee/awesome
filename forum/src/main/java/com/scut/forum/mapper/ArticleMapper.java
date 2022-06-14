package com.scut.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scut.forum.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    @Update(" UPDATE article SET favor_count = favor_count + #{value} WHERE id = #{id} ")
    void updateFavorCount(@Param("id") long id, @Param("value") int value);

    @Update(" UPDATE article SET like_count = like_count + #{value} WHERE id = #{id} ")
    void updateLikeCount(@Param("id") long id, @Param("value") int value);

    @Update(" UPDATE article SET update_time = #{updateTime} WHERE id = #{id} ")
    void updateUpdateTime(@Param("id") long id, @Param("updateTime") long updateTime);
}
