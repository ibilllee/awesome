package com.scut.board.service;

import com.scut.common.dto.request.BoardCommentParam;
import com.scut.common.dto.response.BoardCommentDto;

import java.util.List;

public interface GameCommentService {

    void setNewScore(float score,long scoreCount, long totalScore,long gameId);

    BoardCommentDto addComment(BoardCommentParam comment, long id);

    Boolean deleteComment(long id);

    List<BoardCommentDto> getComment(long gameId);

    Boolean selectGameId(long gameId);
}
