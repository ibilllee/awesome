package com.scut.board.service;

import com.scut.board.entity.GameForBoardEntity;
import com.scut.board.mapper.ViewBoardMapper;
import com.scut.common.dto.response.GameForBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ViewBoardServiceImpl implements ViewBoardService {

    @Autowired
    private ViewBoardMapper viewBoardMapper;

    @Override
    public Float getScoreForOneDecimalPlace(float score) {
        int scoreInt = (int) (score * 10);
        float newScore = scoreInt / 10;
        return newScore;
    }

    @Override
    public Collection<GameForBoardDto> getViewBoardDataByTime() {

        Collection<GameForBoardEntity> gameInfoCollectionByTime;
        try {
            gameInfoCollectionByTime = viewBoardMapper.getGameInfoByTime();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return getGameForBoardDto(gameInfoCollectionByTime);
    }

    @Override
    public Collection<GameForBoardDto> getViewBoardDataByScore() {

        Collection<GameForBoardEntity> gameInfoCollectionByScore;
        try {
            gameInfoCollectionByScore = viewBoardMapper.getGameInfoByScore();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return getGameForBoardDto(gameInfoCollectionByScore);
    }

    private Collection<GameForBoardDto> getGameForBoardDto(Collection<GameForBoardEntity> gameInfoCollection) {

        GameForBoardEntity[] gameInfoList = (GameForBoardEntity[]) gameInfoCollection.toArray();
        Collection<GameForBoardDto> dataList = null;
        for (int i = 0; i < gameInfoList.length; i++) {
            GameForBoardDto data = new GameForBoardDto();
            GameForBoardEntity gameInfo = gameInfoList[i];
            data.setId(gameInfo.getId());
            data.setName(gameInfo.getName());
            data.setCover(gameInfo.getCover());
            data.setLogo(gameInfo.getLogo());
            data.setClassify(gameInfo.getClassify());
            float score = getScoreForOneDecimalPlace(gameInfo.getScore());
            data.setScore(score);
            dataList.add(data);
        }
        return dataList;
    }
}
