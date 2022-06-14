package com.scut.forum.controller;

import com.scut.common.dto.request.ArticleListForMeParam;
import com.scut.common.dto.request.ArticleListParam;
import com.scut.common.dto.request.ArticleParam;
import com.scut.common.dto.request.IdParam;
import com.scut.common.dto.response.ArticleDto;
import com.scut.common.dto.response.ForumDto;
import com.scut.common.response.MultiResponse;
import com.scut.common.response.SingleResponse;
import com.scut.forum.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;

import static com.scut.common.constant.HttpConstant.USER_ID_HEADER;

@RestController
@RequestMapping("/article")
@Api(value = "article", description = "文章")
@Slf4j
public class ArticleController {
    @Resource
    private ArticleService articleService;

    @PostMapping("/submit")
    @ApiOperation(value = "/submit", notes = "新增文章")
    public SingleResponse<ArticleDto> submit(@RequestBody ArticleParam articleParam,
                                             @RequestHeader(USER_ID_HEADER) Long userId) {
        ArticleDto articleDto = articleService.submit(articleParam, userId);

        if (articleDto == null)
            return new SingleResponse<ArticleDto>().unknown(null, "未知错误");
        return new SingleResponse<ArticleDto>().success(articleDto);
    }

    @GetMapping("/get")
    @ApiOperation(value = "/get", notes = "获取文章")
    public SingleResponse<ArticleDto> get(long id) {
        ArticleDto articleDto = articleService.get(id);
        if (articleDto == null)
            return new SingleResponse<ArticleDto>().error(null, 4007, "获取某个文章不存在");
        return new SingleResponse<ArticleDto>().success(articleDto);
    }

    @GetMapping("/list/byHot")
    @ApiOperation(value = "/list/byHot", notes = "获取文章列表 （按热度）")
    // TODO: 按热度排序
    public MultiResponse<ArticleDto> listByHot(ArticleListParam articleListParam) {
        return null;
    }

    @GetMapping("/list/byTime")
    @ApiOperation(value = "/list/byTime", notes = "获取文章列表 （按时间）")
    public MultiResponse<ArticleDto> listByTime(ArticleListParam articleListParam) {
        List<ArticleDto> result = articleService.getListByTime(articleListParam);
        if (result == null)
            return new MultiResponse<ArticleDto>().unknown(null, "未知错误");
        return new MultiResponse<ArticleDto>().success(result);
    }

    @GetMapping("/myList")
    @ApiOperation(value = "/myList", notes = "获取我的文章列表")
    public MultiResponse<ArticleDto> myList(ArticleListForMeParam articleListForMeParam,
                                            @RequestHeader(USER_ID_HEADER) Long userId) {
        List<ArticleDto> result = articleService.getMyList(articleListForMeParam, userId);
        if (result == null)
            return new MultiResponse<ArticleDto>().unknown(null, "未知错误");
        return new MultiResponse<ArticleDto>().success(result);
    }

    @DeleteMapping("/remove")
    @ApiOperation(value = "/remove", notes = "删除文章")
    public SingleResponse<Boolean> delete(@RequestBody IdParam idParam,
                                          @RequestHeader(USER_ID_HEADER) Long userId) {
        int result = articleService.delete(idParam.getId(), userId);
        if (result == -1)
            return new SingleResponse<Boolean>().error(false, 4013, "该文章不属于你");
        else if (result == 0)
            return new SingleResponse<Boolean>().error(false, 4012, "删除某个文章不存在");
        return new SingleResponse<Boolean>().success(true);
    }

    @PostMapping("/favor")
    @ApiOperation(value = "/favor", notes = "收藏文章")
    public SingleResponse<Boolean> favor(@RequestBody IdParam idParam,
                                         @RequestHeader(USER_ID_HEADER) long userId) {
        int result = articleService.favor(idParam.getId(), userId);
        if (result == -1)
            return new SingleResponse<Boolean>().error(false, 4008, "收藏某个文章不存在");
        else if (result == -2)
            return new SingleResponse<Boolean>().error(false, 4009, "您已经收藏过该文章");
        else if (result == 0)
            return new SingleResponse<Boolean>().unknown(false, "未知错误");
        return new SingleResponse<Boolean>().success(true);
    }

    @DeleteMapping("/unfavor")
    @ApiOperation(value = "/unfavor", notes = "取消收藏文章")
    public SingleResponse<Boolean> unfavor(@RequestBody IdParam idParam,
                                           @RequestHeader(USER_ID_HEADER) long userId) {
        int result = articleService.unfavor(idParam.getId(), userId);
        if (result == -1)
            return new SingleResponse<Boolean>().error(false, 4008, "收藏某个文章不存在");
        else if (result == -2)
            return new SingleResponse<Boolean>().error(false, 4009, "您未收藏过该文章");
        else if (result == 0)
            return new SingleResponse<Boolean>().unknown(false, "未知错误");
        return new SingleResponse<Boolean>().success(true);
    }

    @GetMapping("/isFavor")
    @ApiOperation(value = "/isFavor", notes = "是否收藏文章")
    public SingleResponse<Boolean> isFavor(long id,
                                           @RequestHeader(USER_ID_HEADER) long userId) {
        boolean result = articleService.isFavor(userId, id);
        return new SingleResponse<Boolean>().success(result);
    }

    @GetMapping("/favor/list")
    @ApiOperation(value = "/favor/list", notes = "获得收藏的文章")
    public MultiResponse<ArticleDto> favorList(ArticleListForMeParam articleListForMeParam,
                                               @RequestHeader(USER_ID_HEADER) long userId) {
        List<ArticleDto> result = articleService.favorList(articleListForMeParam, userId);
        return new MultiResponse<ArticleDto>().success(result);
    }


    @PostMapping("/like")
    @ApiOperation(value = "/like", notes = "点赞文章")
    public SingleResponse<Boolean> like(@RequestBody IdParam idParam,
                                        @RequestHeader(USER_ID_HEADER) long userId) {
        int result = articleService.like(idParam.getId(), userId);
        if (result == -1)
            return new SingleResponse<Boolean>().error(false, 4010, "点赞某个文章不存在");
        else if (result == -2)
            return new SingleResponse<Boolean>().error(false, 4011, "您已经点赞过该文章");
        else if (result == 0)
            return new SingleResponse<Boolean>().unknown(false, "未知错误");
        return new SingleResponse<Boolean>().success(true);
    }

    @DeleteMapping("/unlike")
    @ApiOperation(value = "/unlike", notes = "取消点赞文章")
    public SingleResponse<Boolean> unlike(@RequestBody IdParam idParam,
                                          @RequestHeader(USER_ID_HEADER) long userId) {
        int result = articleService.unlike(idParam.getId(), userId);
        if (result == -1)
            return new SingleResponse<Boolean>().error(false, 4010, "点赞某个文章不存在");
        else if (result == -2)
            return new SingleResponse<Boolean>().error(false, 4011, "您未点赞过该文章");
        else if (result == 0)
            return new SingleResponse<Boolean>().unknown(false, "未知错误");
        return new SingleResponse<Boolean>().success(true);
    }

    @GetMapping("/isLike")
    @ApiOperation(value = "/isLike", notes = "是否喜欢文章")
    public SingleResponse<Boolean> isLike(long id,
                                          @RequestHeader(USER_ID_HEADER) long userId) {
        boolean result = articleService.isLike(userId, id);
        return new SingleResponse<Boolean>().success(result);
    }
}
