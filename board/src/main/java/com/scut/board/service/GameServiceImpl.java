package com.scut.board.service;

import com.scut.common.dto.response.GameDetailsDto;
import com.scut.common.dto.response.SearchGameListDto;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class GameServiceImpl implements GameService{

    @Override
    public String getGameUrl(long gameId) {
        return null;
    }

    @Override
    public GameDetailsDto getGameDetails(long gameId) {
        return null;
    }

    @Override
    public Collection<SearchGameListDto> getGameListBySearch(String Keyword) {
        return null;
    }
}
