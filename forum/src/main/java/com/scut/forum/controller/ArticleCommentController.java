package com.scut.forum.controller;

import com.scut.common.dto.request.ArticleCommentListParam;
import com.scut.common.dto.request.ArticleCommentParam;
import com.scut.common.dto.request.IdParam;
import com.scut.common.dto.response.ArticleCommentDto;
import com.scut.common.response.MultiResponse;
import com.scut.common.response.SingleResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article-comment")
@Api(value = "article_comment", description = "文章评论")
@Slf4j
public class ArticleCommentController {
    @PostMapping("/submit")
    @ApiOperation(value = "/submit", notes = "新增文章评论")
    public SingleResponse<ArticleCommentDto> submit(@RequestBody ArticleCommentParam articleCommentParam) {
        return null;
    }

    @GetMapping("/get")
    @ApiOperation(value = "/get", notes = "获取文章评论")
    public SingleResponse<ArticleCommentDto> get( long id) {
        return null;
    }

    @GetMapping("/list")
    @ApiOperation(value = "/list", notes = "获取文章评论列表")
    public MultiResponse<ArticleCommentDto> list( ArticleCommentListParam articleCommentListParam) {
        return null;
    }

    @DeleteMapping("/remove")
    @ApiOperation(value = "/remove", notes = "删除文章评论")
    public SingleResponse<Integer> delete(@RequestBody IdParam idParam) {
        return null;
    }
}
