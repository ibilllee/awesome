package com.scut.board.service;

import com.scut.common.dto.response.GameDetailsDto;
import com.scut.common.dto.response.SearchGameListDto;

import java.util.List;

public interface GameService {
    String getGameUrl(long gameId);
    GameDetailsDto getGameDetails(long gameId);
    List<SearchGameListDto> getGameListBySearch(String Keyword);
}
