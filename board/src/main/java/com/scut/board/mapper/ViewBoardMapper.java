package com.scut.board.mapper;

import com.scut.board.entity.GameForBoard;

import java.util.Collection;

public interface ViewBoardMapper {
    Collection<GameForBoard> getGameInfoByScore();

    Collection<GameForBoard> getGameInfoByTime();
}
