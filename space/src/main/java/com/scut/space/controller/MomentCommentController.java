package com.scut.space.controller;

import com.scut.common.dto.request.MomentCommentListParam;
import com.scut.common.dto.request.MomentCommentParam;
import com.scut.common.dto.response.MomentCommentDto;
import com.scut.common.response.MultiResponse;
import com.scut.common.response.SingleResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/moment-comment")
@Api(value = "moment_comment", description = "动态评论")
@Slf4j
public class MomentCommentController {
    @PostMapping("/submit")
    @ApiOperation(value = "/submit", notes = "新增动态评论")
    public SingleResponse<MomentCommentDto> submit(@RequestBody MomentCommentParam momentCommentParam) {
        return null;
    }

    @GetMapping("/get")
    @ApiOperation(value = "/get", notes = "获取动态评论")
    public SingleResponse<MomentCommentDto> get(@RequestParam long id) {
        return null;
    }

    @GetMapping("/list")
    @ApiOperation(value = "/list", notes = "获取动态评论列表")
    public MultiResponse<MomentCommentDto> list(@RequestParam MomentCommentListParam momentCommentListParam) {
        return null;
    }

    @DeleteMapping("/remove")
    @ApiOperation(value = "/remove", notes = "删除动态评论")
    public SingleResponse<Integer> delete(@RequestBody long id) {
        return null;
    }
}
