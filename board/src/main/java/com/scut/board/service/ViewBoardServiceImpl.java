package com.scut.board.service;

import com.scut.board.entity.GameForBoard;
import com.scut.board.mapper.ViewBoardMapper;
import com.scut.common.dto.response.GameForBoardDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ViewBoardServiceImpl implements ViewBoardService {

    @Resource
    private ViewBoardMapper viewBoardMapper;

    public Float getScoreForOneDecimalPlace(float score) {
        int scoreInt = (int) (score * 10);
        float newScore = (float) (scoreInt / 10.0);
        return newScore;
    }

    @Transactional
    public List<GameForBoardDto> getViewBoardDataByTime() {

        List<GameForBoard> gameInfoCollectionByTime;
        try {
            gameInfoCollectionByTime = viewBoardMapper.selectGameInfoByTime();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return getGameForBoardDto(gameInfoCollectionByTime);
    }

    public List<GameForBoardDto> getViewBoardDataByScore() {

        List<GameForBoard> gameInfoCollectionByScore;
        try {
            gameInfoCollectionByScore = viewBoardMapper.selectGameInfoByScore();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return getGameForBoardDto(gameInfoCollectionByScore);
    }

    public List<GameForBoardDto> getGameForBoardDto(List<GameForBoard> gameInfoList) {
        List<GameForBoardDto> dataList = new ArrayList<>();
        for (int i = 0; i < gameInfoList.size(); i++) {
            GameForBoard gameInfo = gameInfoList.get(i);
            GameForBoardDto data = new GameForBoardDto(gameInfo.getId(), (long) i + 1, gameInfo.getName(), gameInfo.getCover(), gameInfo.getLogo(), gameInfo.getClassify(), getScoreForOneDecimalPlace(gameInfo.getScore()));
            dataList.add(data);
        }
        return dataList;
    }
}
