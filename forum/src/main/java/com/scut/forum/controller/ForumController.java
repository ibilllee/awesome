package com.scut.forum.controller;

import com.scut.common.dto.request.ForumListParam;
import com.scut.common.dto.request.ForumParam;
import com.scut.common.dto.response.ForumDto;
import com.scut.common.response.MultiResponse;
import com.scut.common.response.SingleResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forum")
@Api(value = "forum", description = "论坛")
public class ForumController {
    @PostMapping("/submit")
    @ApiOperation(value = "/submit", notes = "新增论坛")
    public SingleResponse<ForumDto> submit(@RequestBody ForumParam forumParam) {
        return null;
    }

    @GetMapping("/get")
    @ApiOperation(value = "/get", notes = "获取论坛")
    public SingleResponse<ForumDto> get(@RequestParam long id) {
        return null;
    }

    @GetMapping("/list")
    @ApiOperation(value = "/list", notes = "获取论坛列表")
    public MultiResponse<ForumDto> list(@RequestParam ForumListParam forumListParam) {
        return null;
    }

    @GetMapping("/myList")
    @ApiOperation(value = "/myList", notes = "获取我收藏的论坛列表")
    public MultiResponse<ForumDto> myList(@RequestParam ForumListParam forumListParam) {
        return null;
    }

    @GetMapping("/search")
    @ApiOperation(value = "/search", notes = "获取论坛列表（搜索）")
    public MultiResponse<ForumDto> search(@RequestParam ForumListParam forumListParam) {
        return null;
    }

    @DeleteMapping("/remove")
    @ApiOperation(value = "/remove", notes = "删除论坛")
    public SingleResponse<Integer> delete(@RequestBody long id) {
        return null;
    }

    @GetMapping("/list/tag")
    @ApiOperation(value = "/list/tag", notes = "获取论坛标签列表")
    public MultiResponse<String> listTag(@RequestParam long id) {
        return null;
    }

    @PostMapping("/favor")
    @ApiOperation(value = "/favor", notes = "收藏论坛")
    public SingleResponse<Integer> favor(@RequestBody long id) {
        return null;
    }

    @DeleteMapping("/unfavor")
    @ApiOperation(value = "/unfavor", notes = "取消收藏论坛")
    public SingleResponse<Integer> unfavor(@RequestBody long id) {
        return null;
    }
}
