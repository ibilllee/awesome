package com.scut.forum.controller;

import com.scut.common.dto.request.ArticleListParam;
import com.scut.common.dto.request.ArticleParam;
import com.scut.common.dto.request.IdParam;
import com.scut.common.dto.response.ArticleDto;
import com.scut.common.response.MultiResponse;
import com.scut.common.response.SingleResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
@Api(value = "article", description = "文章")
@Slf4j
public class ArticleController {

    @PostMapping("/submit")
    @ApiOperation(value = "/submit", notes = "新增文章")
    public SingleResponse<ArticleDto> submit(@RequestBody ArticleParam articleParam) {
        return null;
    }

    @GetMapping("/get")
    @ApiOperation(value = "/get", notes = "获取文章")
    public SingleResponse<ArticleDto> get( long id) {
        return null;
    }

    @GetMapping("/list/byHot")
    @ApiOperation(value = "/list/byHot", notes = "获取文章列表 （按热度）")
    public MultiResponse<ArticleDto> listByHot( ArticleListParam articleListParam) {
        return null;
    }

    @GetMapping("/list/byTime")
    @ApiOperation(value = "/list/byTime", notes = "获取文章列表 （按时间）")
    public MultiResponse<ArticleDto> listByTime( ArticleListParam articleListParam) {
        return null;
    }

    @GetMapping("/myList")
    @ApiOperation(value = "/myList", notes = "获取我的文章列表")
    public MultiResponse<ArticleDto> myList( ArticleListParam articleListParam) {
        return null;
    }

    @DeleteMapping("/remove")
    @ApiOperation(value = "/remove", notes = "删除文章")
    public SingleResponse<Integer> delete(@RequestBody IdParam idParam) {
        return null;
    }

    @PostMapping("/favor")
    @ApiOperation(value = "/favor", notes = "收藏文章")
    public SingleResponse<Integer> favor(@RequestBody IdParam idParam) {
        return null;
    }

    @DeleteMapping("/unfavor")
    @ApiOperation(value = "/unfavor", notes = "取消收藏文章")
    public SingleResponse<Integer> unfavor(@RequestBody IdParam idParam) {
        return null;
    }

    @PostMapping("/like")
    @ApiOperation(value = "/like", notes = "点赞文章")
    public SingleResponse<Integer> like(@RequestBody IdParam idParam) {
        return null;
    }

    @DeleteMapping("/unlike")
    @ApiOperation(value = "/unlike", notes = "取消点赞文章")
    public SingleResponse<Integer> unlike(@RequestBody IdParam idParam) {
        return null;
    }
}
