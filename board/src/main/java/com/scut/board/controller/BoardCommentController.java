package com.scut.board.controller;

import com.scut.common.dto.request.BoardCommentParam;
import com.scut.common.dto.response.BoardCommentDto;
import com.scut.common.response.SingleResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/comment")
@RestController
@Api(value = "comment", description = "评论")
@Slf4j
public class BoardCommentController {
    @GetMapping("/submit")
    @ApiOperation(value = "/submit", notes = "添加评论")
    public SingleResponse<BoardCommentDto> addComment(@RequestBody BoardCommentParam comment) {
        return null;
    }

    @GetMapping("/remove")
    @ApiOperation(value = "/remove", notes = "删除评论")
    public SingleResponse<Integer> removeComment(@RequestBody long id) {
        return null;
    }
}
