package com.scut.board.service;

import com.scut.board.entity.GameForBoard;
import com.scut.common.dto.response.GameForBoardDto;

import java.util.List;

public interface ViewBoardService {

    Float getScoreForOneDecimalPlace(float score);

    List<GameForBoardDto> getViewBoardDataByTime();

    List<GameForBoardDto> getViewBoardDataByScore();

    List<GameForBoardDto> getGameForBoardDto(List<GameForBoard> gameInfoCollection);
}
