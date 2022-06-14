package com.scut.forum.controller;

import com.scut.common.dto.request.ForumListParam;
import com.scut.common.dto.request.ForumParam;
import com.scut.common.dto.request.ForumTagParam;
import com.scut.common.dto.request.IdParam;
import com.scut.common.dto.response.ForumDto;
import com.scut.common.dto.response.ForumTagDto;
import com.scut.common.response.MultiResponse;
import com.scut.common.response.PageResponse;
import com.scut.common.response.SingleResponse;
import com.scut.forum.service.ForumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.scut.common.constant.HttpConstant.USER_ID_HEADER;

@RestController
@RequestMapping("/forum")
@Api(value = "forum", description = "论坛")
public class ForumController {
    @Resource
    private ForumService forumService;

    @PostMapping("/submit")
    @ApiOperation(value = "/submit", notes = "新增论坛")
    public SingleResponse<ForumDto> submit(@RequestBody ForumParam forumParam) {
        ForumDto newForum = forumService.submit(forumParam);
        if (newForum == null)
            return new SingleResponse<ForumDto>().unknown(null, "未知错误");
        return new SingleResponse<ForumDto>().success(newForum);
    }

    @GetMapping("/get")
    @ApiOperation(value = "/get", notes = "获取论坛")
    public SingleResponse<ForumDto> get(long id) {
        ForumDto forum = forumService.get(id);
        if (forum == null)
            return new SingleResponse<ForumDto>().error(null, 4002, "获取某个论坛不存在");
        return new SingleResponse<ForumDto>().success(forum);
    }

    @GetMapping("/list")
    @ApiOperation(value = "/list", notes = "获取论坛列表")
    public MultiResponse<ForumDto> list(ForumListParam forumListParam) {
        List<ForumDto> result = forumService.getList(forumListParam);
        if (result == null)
            return new MultiResponse<ForumDto>().unknown(null, "未知错误");
        return new MultiResponse<ForumDto>().success(result);
    }

    @GetMapping("/myList")
    @ApiOperation(value = "/myList", notes = "获取我收藏的论坛列表")
    public MultiResponse<ForumDto> myList(ForumListParam forumListParam,
                                          @RequestHeader(USER_ID_HEADER) long userId) {
        List<ForumDto> result = forumService.getMyList(forumListParam, userId);
        if (result == null)
            return new MultiResponse<ForumDto>().unknown(null, "未知错误");
        return new MultiResponse<ForumDto>().success(result);
    }

    @GetMapping("/search")
    @ApiOperation(value = "/search", notes = "获取论坛列表（搜索）")
    public MultiResponse<ForumDto> search(ForumListParam forumListParam) {
        List<ForumDto> result = forumService.search(forumListParam);
        if (result == null)
            return new MultiResponse<ForumDto>().unknown(null, "未知错误");
        return new MultiResponse<ForumDto>().success(result);
    }

    @DeleteMapping("/remove")
    @ApiOperation(value = "/remove", notes = "删除论坛")
    public SingleResponse<Integer> delete(@RequestBody IdParam idParam) {
        int result = forumService.delete(idParam.getId());
        if (result == 0)
            return new SingleResponse<Integer>().error(null, 4005, "删除某个论坛不存在");
        return new SingleResponse<Integer>().success(result);
    }

    @PostMapping("/submit/tag")
    @ApiOperation(value = "/submit/tag", notes = "新增论坛标签")
    public SingleResponse<ForumTagDto> submitTag(@RequestBody ForumTagParam forumTagParam) {
        ForumTagDto newTag = forumService.submitTag(forumTagParam);
        if (newTag == null)
            return new SingleResponse<ForumTagDto>().unknown(null, "未知错误");
        return new SingleResponse<ForumTagDto>().success(newTag);
    }

    @GetMapping("/list/tag")
    @ApiOperation(value = "/list/tag", notes = "获取论坛标签列表")
    public MultiResponse<ForumTagDto> listTag(long id) {
        List<ForumTagDto> result = forumService.getTagList(id);
        if (result == null)
            return new MultiResponse<ForumTagDto>().unknown(null, "未知错误");
        return new MultiResponse<ForumTagDto>().success(result);
    }

    @DeleteMapping("/remove/tag")
    @ApiOperation(value = "/remove/tag", notes = "删除论坛标签")
    public SingleResponse<Boolean> deleteTag(@RequestBody IdParam idParam) {
        int result = forumService.deleteTag(idParam.getId());
        if (result == 0)
            return new SingleResponse<Boolean>().error(false, 4006, "删除某个论坛标签不存在");
        return new SingleResponse<Boolean>().success(true);
    }

    @GetMapping("/isFavor")
    @ApiOperation(value = "/isFavor", notes = "是否收藏论坛")
    public SingleResponse<Boolean> isFavor(long id,
                                           @RequestHeader(USER_ID_HEADER) long userId) {
        boolean result = forumService.isFavor(userId, id);
        return new SingleResponse<Boolean>().success(result);
    }

    @PostMapping("/favor")
    @ApiOperation(value = "/favor", notes = "收藏论坛")
    public SingleResponse<Boolean> favor(@RequestBody IdParam idParam,
                                         @RequestHeader(USER_ID_HEADER) long userId) {
        int result = forumService.favor(idParam.getId(), userId);
        if (result == -1)
            return new SingleResponse<Boolean>().error(null, 4003, "收藏某个论坛不存在");
        else if (result == -2)
            return new SingleResponse<Boolean>().error(null, 4004, "您已经收藏过该论坛");
        else if (result == 0)
            return new SingleResponse<Boolean>().unknown(null, "未知错误");
        return new SingleResponse<Boolean>().success(true);
    }

    @DeleteMapping("/unfavor")
    @ApiOperation(value = "/unfavor", notes = "取消收藏论坛")
    public SingleResponse<Boolean> unfavor(@RequestBody IdParam idParam,
                                           @RequestHeader(USER_ID_HEADER) long userId) {
        int result = forumService.unfavor(idParam.getId(), userId);
        if (result == -1)
            return new SingleResponse<Boolean>().error(false, 4003, "收藏某个论坛不存在");
        else if (result == -2)
            return new SingleResponse<Boolean>().error(false, 4004, "您未收藏过该论坛");
        else if (result == 0)
            return new SingleResponse<Boolean>().unknown(false, "未知错误");
        return new SingleResponse<Boolean>().success(true);
    }
}
