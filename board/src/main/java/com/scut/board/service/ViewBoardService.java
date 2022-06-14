package com.scut.board.service;

import com.scut.common.dto.response.GameForBoardDto;

import java.util.Collection;

public interface ViewBoardService {

    Float getScoreForOneDecimalPlace(float score);

    Collection<GameForBoardDto> getViewBoardDataByTime();

    Collection<GameForBoardDto> getViewBoardDataByScore();
}
