package com.scut.board.controller;

import com.scut.board.mapper.GameDetailsMapper;
import com.scut.board.service.GameCommentService;
import com.scut.common.dto.request.BoardCommentParam;
import com.scut.common.dto.request.IdParam;
import com.scut.common.dto.response.BoardCommentDto;
import com.scut.common.response.MultiResponse;
import com.scut.common.response.SingleResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/board-comment")
@RestController
@Api(value = "board-comment", description = "评论")
@Slf4j
public class BoardCommentController {

    @Resource
    GameCommentService gameCommentService;

    @Resource
    GameDetailsMapper gameDetailsMapper;

    @PostMapping("/submit")
    @ApiOperation(value = "/submit", notes = "添加评论")
    public SingleResponse<BoardCommentDto> addComment(@RequestBody BoardCommentParam comment,
                                                      @RequestHeader("_USER_ID") long id) {
        BoardCommentDto boardCommentDto = gameCommentService.addComment(comment, id);
        if (boardCommentDto != null) return new SingleResponse<BoardCommentDto>().success(boardCommentDto);
        else return new SingleResponse<BoardCommentDto>().error(null, 3001, "游戏ID不存在");
    }

    @DeleteMapping("/remove")
    @ApiOperation(value = "/remove", notes = "删除评论")
    public SingleResponse<Boolean> removeComment(@RequestBody IdParam id) {
        Boolean removeStatus = gameCommentService.deleteComment(id.getId());
        if (removeStatus.equals(true)) return new SingleResponse<Boolean>().success(true);
        else return new SingleResponse<Boolean>().error(null, 3003, "评论不存在");
    }

    @GetMapping("/get")
    @ApiOperation(value = "/get", notes = "显示评论")
    public MultiResponse<BoardCommentDto> getComment(@RequestParam long gameId) {
        Boolean gameDetails = gameCommentService.selectGameId(gameId);
        if (!gameDetails.booleanValue())
            return new MultiResponse<BoardCommentDto>().error(null, 3001, "游戏ID不存在");
        List<BoardCommentDto> boardCommentDtoList = gameCommentService.getComment(gameId);
        return new MultiResponse<BoardCommentDto>().success(boardCommentDtoList);

    }
}
