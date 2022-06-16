package com.scut.board.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scut.board.entity.GameDetails;
import org.apache.ibatis.annotations.*;

@Mapper
public interface GameDetailsMapper extends BaseMapper<GameDetails> {

    @Select("SELECT download_link FROM game where id = #{gameId}")
    String selectUrl(@Param("gameId") long gameId);

    @Select("SElECT * FROM game where id = #{gameId}")
    @ResultType(GameDetails.class)
    GameDetails selectGameDetails(@Param("gameId") long gameId);

    @Update("UPDATE game SET score=#{score},score_count=#{scoreCount},total_score=#{totalScore} where id = #{gameId}")
    void updateGameScore(@Param("score") float score,@Param("scoreCount") long scoreCount,@Param("totalScore") long totalScore,@Param("gameId") long gameId);
}
