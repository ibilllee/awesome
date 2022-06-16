package com.scut.forum.controller;

import com.scut.common.dto.request.ArticleCommentListParam;
import com.scut.common.dto.request.ArticleCommentParam;
import com.scut.common.dto.request.IdParam;
import com.scut.common.dto.response.ArticleCommentDto;
import com.scut.common.dto.response.ArticleDto;
import com.scut.common.response.MultiResponse;
import com.scut.common.response.SingleResponse;
import com.scut.forum.service.ArticleCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;

import static com.scut.common.constant.HttpConstant.USER_ID_HEADER;

@RestController
@RequestMapping("/article-comment")
@Api(value = "article_comment", description = "文章评论")
@Slf4j
public class ArticleCommentController {
    @Resource
    private ArticleCommentService articleCommentService;

    @PostMapping("/submit")
    @ApiOperation(value = "/submit", notes = "新增文章评论")
    public SingleResponse<ArticleCommentDto> submit(@RequestBody ArticleCommentParam articleCommentParam,
                                                    @RequestHeader(USER_ID_HEADER) Long userId) {
        ArticleCommentDto articleCommentDto = articleCommentService.submit(articleCommentParam, userId);
        if (articleCommentDto == null)
            return new SingleResponse<ArticleCommentDto>().unknown(null, "未知错误");
        else if (articleCommentDto.getId() == -1)
            return new SingleResponse<ArticleCommentDto>().error(null, 4015, "回复的评论不存在");
        else if (articleCommentDto.getId() == -2)
            return new SingleResponse<ArticleCommentDto>().error(null, 4018, "回复的文章不存在");
        else if (articleCommentDto.getId() == -3)
            return new SingleResponse<ArticleCommentDto>().error(null, 4019, "评论不可回复");

        return new SingleResponse<ArticleCommentDto>().success(articleCommentDto);
    }

    @GetMapping("/get")
    @ApiOperation(value = "/get", notes = "获取文章评论")
    public SingleResponse<ArticleCommentDto> get(long id) {
        ArticleCommentDto articleCommentDto = articleCommentService.get(id);
        if (articleCommentDto == null)
            return new SingleResponse<ArticleCommentDto>().error(null, 4014, "获得某个评论不存在");
        return new SingleResponse<ArticleCommentDto>().success(articleCommentDto);
    }

    @GetMapping("/list/{articleId}")
    @ApiOperation(value = "/list/{articleId}", notes = "获取文章评论列表")
    public MultiResponse<ArticleCommentDto> list(ArticleCommentListParam articleCommentListParam,
                                                 @PathVariable Long articleId) {
        List<ArticleCommentDto> list = articleCommentService.list(articleCommentListParam, articleId);
        if (list == null)
            return new MultiResponse<ArticleCommentDto>().unknown(null, "未知错误");
        return new MultiResponse<ArticleCommentDto>().success(list);
    }

    @GetMapping("/list/{articleId}/{parentId}")
    @ApiOperation(value = "/list/{parentId}/{parentId}", notes = "获取子评论列表")
    public MultiResponse<ArticleCommentDto> listByParentId(ArticleCommentListParam articleCommentListParam,
                                                           @PathVariable Long articleId,
                                                           @PathVariable Long parentId) {
        List<ArticleCommentDto> list = articleCommentService.listByParentId(articleCommentListParam, articleId, parentId);
        if (list == null)
            return new MultiResponse<ArticleCommentDto>().unknown(null, "未知错误");
        return new MultiResponse<ArticleCommentDto>().success(list);
    }

    @DeleteMapping("/remove")
    @ApiOperation(value = "/remove", notes = "删除文章评论")
    public SingleResponse<Boolean> delete(@RequestBody IdParam idParam,
                                          @RequestHeader(USER_ID_HEADER) Long userId) {
        int result = articleCommentService.delete(idParam.getId(), userId);
        if (result == -1)
            return new SingleResponse<Boolean>().error(false, 4017, "该评论不属于你");
        else if (result == 0)
            return new SingleResponse<Boolean>().error(false, 4016, "删除某个评论不存在");
        return new SingleResponse<Boolean>().success(true);
    }
}
