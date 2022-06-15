package com.scut.board.service;

import com.scut.board.entity.GameComment;
import com.scut.board.entity.GameDetails;
import com.scut.board.feign.UserFeignService;
import com.scut.board.mapper.GameCommentMapper;
import com.scut.board.mapper.GameDetailsMapper;
import com.scut.common.dto.request.BoardCommentParam;
import com.scut.common.dto.response.BoardCommentDto;
import com.scut.common.dto.response.UserAvatarAndUsernameDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static java.lang.Math.max;

@Service
public class GameCommentServiceImpl implements GameCommentService {

    @Resource
    GameCommentMapper gameCommentMapper;

    @Resource
    GameDetailsMapper gameDetailsMapper;

    @Resource
    private UserFeignService userFeignService;

    @Override
    @Transactional
    public void setNewScore(float score, long scoreCount, long totalScore, long gameId) {
        gameDetailsMapper.updateGameScore(score, scoreCount, totalScore, gameId);
    }

    @Override
    @Transactional
    public BoardCommentDto addComment(BoardCommentParam comment, long id) {
        long gameId = comment.getId();
        String content = comment.getComment();
        long score = comment.getScore();
        long nowTime = System.currentTimeMillis();
        long commentId = gameCommentMapper.insertComment(id, gameId, content, score, nowTime);
        System.out.println(commentId);
        GameDetails gameDetails = gameDetailsMapper.selectGameDetails(gameId);
        long scoreCount = gameDetails.getScoreCount() + 1;
        long totalScore = gameDetails.getTotalScore() + score;
        float newScore = (float) totalScore / scoreCount;
        setNewScore(newScore, scoreCount, totalScore, gameId);
        GameComment gameComment = new GameComment(commentId, id, gameId, content, score, nowTime);
        UserAvatarAndUsernameDto avatarAndUsernameDto = userFeignService.getAvatarAndUsername(gameComment.getUserId()).getData();
        return new BoardCommentDto(commentId, gameId, id, avatarAndUsernameDto.getUsername(), avatarAndUsernameDto.getAvatar(), content, score, nowTime);
    }

    @Override
    @Transactional
    public Boolean deleteComment(long id) {
        GameComment gameComment = gameCommentMapper.selectComment(id);
        if (gameComment == null) return false;
        long gameId = gameComment.getGameId();
        long gameScore = gameComment.getGameScore();
        GameDetails gameDetails = gameDetailsMapper.selectGameDetails(gameId);
        long scoreCount = gameDetails.getScoreCount() - 1;
        long totalScore = gameDetails.getTotalScore() - gameScore;
        float score = (float) totalScore / max(scoreCount, 1);
        setNewScore(score, scoreCount, totalScore, gameId);
        gameCommentMapper.deleteComment(id);
        return true;
    }

    @Override
    @Transactional
    public List<BoardCommentDto> getComment(long gameId) {
        List<GameComment> gameCommentList = gameCommentMapper.selectCommentForGame(gameId);
        List<BoardCommentDto> boardCommentDtoList = null;
        for (GameComment gameComment : gameCommentList) {
            UserAvatarAndUsernameDto avatarAndUsernameDto = userFeignService.getAvatarAndUsername(gameComment.getUserId()).getData();
            BoardCommentDto boardCommentDto = new BoardCommentDto(gameComment.getId(),
                    gameComment.getGameId(),
                    gameComment.getUserId(),
                    avatarAndUsernameDto.getUsername(),
                    avatarAndUsernameDto.getAvatar(),
                    gameComment.getContent(),
                    gameComment.getGameScore(),
                    gameComment.getCreateTime());
            boardCommentDtoList.add(boardCommentDto);
        }
        return boardCommentDtoList;
    }

    @Override
    public Boolean selectGameId(long gameId) {
        GameDetails gameDetails = gameDetailsMapper.selectGameDetails(gameId);
        if (gameDetails == null) return false;
        else return true;
    }


}
