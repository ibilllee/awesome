package com.scut.board.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scut.board.entity.GameComment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GameCommentMapper extends BaseMapper<GameComment> {

    @Select("SELECT * FROM game_comment where game_id = #{gameId} order by create_time desc")
    @ResultType(GameComment.class)
    List<GameComment> selectCommentForGame(@Param("gameId") long gameId);

    @Select("SELECT * FROM game_comment where id = #{id}")
    @ResultType(GameComment.class)
    GameComment selectComment(@Param("id") long id);


    @Insert("INSERT INTO game_comment(user_id,game_id,content,game_score,create_time) VALUE(#{userId},#{gameId},#{content},#{score},#{createTime})")
    long insertComment(@Param("userId") long userId,
                        @Param("gameId") long gameId,
                        @Param("content") String content,
                        @Param("score") long score,
                       @Param("createTime") long createTime
    );

    @Delete(" DELETE FROM game_comment WHERE id = #{id} ")
    void deleteComment(@Param("id") long id);
}
