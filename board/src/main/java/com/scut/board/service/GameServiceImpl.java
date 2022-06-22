package com.scut.board.service;

import com.scut.board.entity.GameDetails;
import com.scut.board.entity.SearchGameList;
import com.scut.board.mapper.GameDetailsMapper;
import com.scut.board.mapper.SearchGameListMapper;
import com.scut.common.dto.request.GameParam;
import com.scut.common.dto.response.GameDetailsDto;
import com.scut.common.dto.response.SearchGameListDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Resource
    private GameDetailsMapper gameDetailsMapper;

    @Resource
    private SearchGameListMapper searchGameListMapper;

    private String getNewKeyWord(String KeyWord) {
        StringBuilder newKeyWord = new StringBuilder("%");
        for (int i = 0; i < KeyWord.length(); i++) {
            newKeyWord.append(KeyWord.charAt(i)).append("%");
        }
        System.out.println(newKeyWord);
        return newKeyWord.toString();
    }

    @Override
    public String getGameUrl(long gameId) {
        return gameDetailsMapper.selectUrl(gameId);
    }

    @Override
    public GameDetailsDto getGameDetails(long gameId) {
        GameDetails gameDetails = gameDetailsMapper.selectGameDetails(gameId);
        return new GameDetailsDto(gameDetails.getId(),
                gameDetails.getName(),
                gameDetails.getCover(),
                gameDetails.getLogo(),
                gameDetails.getIssuedTime(),
                gameDetails.getDownloadLink(),
                gameDetails.getClassify(),
                gameDetails.getScore(),
                gameDetails.getScoreCount(),
                gameDetails.getTotalScore(),
                gameDetails.getDescription());
    }

    @Override
    public List<SearchGameListDto> getGameListBySearch(String Keyword) {
        String newKeyWord = getNewKeyWord(Keyword);
        List<SearchGameList> searchGameListList = searchGameListMapper.selectSearchGameList(newKeyWord);
        List<SearchGameListDto> searchGameListDtoList = new ArrayList<>();
        for (SearchGameList searchGameList : searchGameListList) {
            SearchGameListDto searchGameListDto = new SearchGameListDto(searchGameList.getId(),
                    searchGameList.getScore(),
                    searchGameList.getName(),
                    searchGameList.getClassify());
            searchGameListDtoList.add(searchGameListDto);
        }
        return searchGameListDtoList;
    }

    @Override
    public Boolean addGame(GameParam game) {
        long gameCount = gameDetailsMapper.selectGameDetailsByName(game.getName());
        if (gameCount != 0) return false;
        gameDetailsMapper.addGame(game.getName(),
                game.getCover(),
                game.getLogo(),
                game.getIssuedTime(),
                game.getDownloadLink(),
                game.getClassify(),
                game.getDescription());
        return true;
    }
}
