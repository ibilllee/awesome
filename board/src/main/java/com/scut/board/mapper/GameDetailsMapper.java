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

    @Select("SElECT count(*) FROM game where name = #{name}")
    long selectGameDetailsByName(@Param("name") String name);

    @Insert("INSERT INTO game(name,cover,logo,issued_time,download_link,classify,score,score_count,total_score,description) VALUE(#{name},#{cover},#{logo},#{issued_time},#{download_link},#{classify},0,0,0,#{description})")
    void addGame(@Param("name") String name,
                       @Param("cover") String cover,
                       @Param("logo") String logo,
                       @Param("issued_time") long issuedTime,
                       @Param("download_link") String downloadLink,
                       @Param("classify") String classify,
                       @Param("description") String description
    );

    @Update("UPDATE game SET score=#{score},score_count=#{scoreCount},total_score=#{totalScore} where id = #{gameId}")
    void updateGameScore(@Param("score") float score,@Param("scoreCount") long scoreCount,@Param("totalScore") long totalScore,@Param("gameId") long gameId);
}
