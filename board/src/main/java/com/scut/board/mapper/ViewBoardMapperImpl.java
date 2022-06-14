package com.scut.board.mapper;

import com.scut.board.entity.GameForBoard;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class ViewBoardMapperImpl implements ViewBoardMapper {

    @Override
    public Collection<GameForBoard> getGameInfoByScore() {
        //按照分数顺序读sql
        String sql = "";

        return null;
    }

    @Override
    public Collection<GameForBoard> getGameInfoByTime() {
        //按照时间顺序读sql
        String sql = "";
        return null;
    }
}
