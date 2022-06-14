package com.scut.board.mapper;

import com.scut.board.entity.GameForBoardEntity;

import java.util.Collection;

public interface ViewBoardMapper {
    Collection<GameForBoardEntity> getGameInfoByScore();

    Collection<GameForBoardEntity> getGameInfoByTime();
}
