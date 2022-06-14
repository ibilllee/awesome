package com.scut.board.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scut.board.entity.GameForBoard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ViewBoardMapper extends BaseMapper<GameForBoard> {

    @Select(" SELECT id, name, cover, logo, classify, score FROM game order by score desc")
    @ResultType(GameForBoard.class)
    List<GameForBoard> selectGameInfoByScore();

    @Select(" SELECT id, name, cover, logo, classify, score FROM game order by issued_time desc")
    @ResultType(GameForBoard.class)
    List<GameForBoard> selectGameInfoByTime();
}
