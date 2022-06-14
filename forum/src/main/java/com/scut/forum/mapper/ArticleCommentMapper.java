package com.scut.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scut.forum.entity.ArticleComment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ArticleCommentMapper extends BaseMapper<ArticleComment> {
    @Update(" UPDATE article_comment SET reply_count = reply_count + #{value} WHERE id = #{id} ")
    void updateReplyCount(@Param("id") long id, @Param("value") int value);

    @Delete(" DELETE FROM article_comment WHERE reply_id = #{id} ")
    int deleteByParentId(long id);
}
