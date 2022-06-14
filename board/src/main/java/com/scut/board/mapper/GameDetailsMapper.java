package com.scut.board.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scut.board.entity.GameDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GameDetailsMapper extends BaseMapper<GameDetails> {

    @Select("SELECT download_link FROM game where id = #{gameId}")
    String selectUrl(@Param("gameId") long gameId);

    @Select("SElECT * FROM game where id = #{gameId}")
    @ResultType(GameDetails.class)
    GameDetails selectGameDetails(@Param("gameId") long gameId);
}
