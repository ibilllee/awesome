package com.scut.space.controller;

import com.scut.common.dto.request.IdParam;
import com.scut.common.dto.request.MomentCommentListParam;
import com.scut.common.dto.request.MomentCommentParam;
import com.scut.common.dto.response.MomentCommentDto;
import com.scut.common.response.MultiResponse;
import com.scut.common.response.SingleResponse;
import com.scut.space.service.MomentCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;

import static com.scut.common.constant.HttpConstant.USER_ID_HEADER;

@RestController
@RequestMapping("/moment-comment")
@Api(value = "moment_comment", description = "动态评论")
@Slf4j
public class MomentCommentController {
    @Resource
    private MomentCommentService momentCommentService;

    @PostMapping("/submit")
    @ApiOperation(value = "/submit", notes = "新增动态评论")
    public SingleResponse<MomentCommentDto> submit(@RequestBody MomentCommentParam momentCommentParam,
                                                   @RequestHeader(USER_ID_HEADER) Long userId) {
        MomentCommentDto momentCommentDto = momentCommentService.submit(momentCommentParam, userId);
        if (momentCommentDto == null)
            return new SingleResponse<MomentCommentDto>().unknown(null, "未知错误");
        else if (momentCommentDto.getId() == -1)
            return new SingleResponse<MomentCommentDto>().error(null, 2007, "回复的评论不存在");
        else if (momentCommentDto.getId() == -2)
            return new SingleResponse<MomentCommentDto>().error(null, 2010, "回复的动态不存在");
        else if (momentCommentDto.getId() == -3)
            return new SingleResponse<MomentCommentDto>().error(null, 2011, "评论不可回复");

        return new SingleResponse<MomentCommentDto>().success(momentCommentDto);
    }

    @GetMapping("/get")
    @ApiOperation(value = "/get", notes = "获取动态评论")
    public SingleResponse<MomentCommentDto> get(long id) {
        MomentCommentDto momentCommentDto = momentCommentService.get(id);
        if (momentCommentDto == null)
            return new SingleResponse<MomentCommentDto>().error(null, 2006, "获得某个评论不存在");
        return new SingleResponse<MomentCommentDto>().success(momentCommentDto);
    }

    @GetMapping("/list/{momentId}")
    @ApiOperation(value = "/list/{momentId}", notes = "获取动态评论列表")
    public MultiResponse<MomentCommentDto> list(MomentCommentListParam momentCommentListParam,
                                                @PathVariable long momentId) {
        List<MomentCommentDto> momentCommentDtoList = momentCommentService.list(momentCommentListParam, momentId);
        if (momentCommentDtoList == null)
            return new MultiResponse<MomentCommentDto>().unknown(null, "未知错误");
        return new MultiResponse<MomentCommentDto>().success(momentCommentDtoList);
    }

    @GetMapping("/list/{momentId}/{parentId}")
    @ApiOperation(value = "/list/{momentId}/{parentId}", notes = "获取子评论列表")
    public MultiResponse<MomentCommentDto> listByParentId(MomentCommentListParam momentCommentListParam,
                                                          @PathVariable Long momentId,
                                                          @PathVariable Long parentId) {
        List<MomentCommentDto> list = momentCommentService.listByParentId(momentCommentListParam, momentId, parentId);
        if (list == null)
            return new MultiResponse<MomentCommentDto>().unknown(null, "未知错误");
        return new MultiResponse<MomentCommentDto>().success(list);
    }

    @DeleteMapping("/remove")
    @ApiOperation(value = "/remove", notes = "删除动态评论")
    public SingleResponse<Boolean> delete(@RequestBody IdParam idParam,
                                          @RequestHeader(USER_ID_HEADER) Long userId) {
        int result = momentCommentService.delete(idParam.getId(), userId);
        if (result == -1)
            return new SingleResponse<Boolean>().error(false, 2009, "该评论不属于你");
        else if (result == 0)
            return new SingleResponse<Boolean>().error(false, 2008, "删除某个评论不存在");
        return new SingleResponse<Boolean>().success(true);
    }
}
