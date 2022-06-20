package com.scut.space.controller;

import com.scut.common.dto.request.IdParam;
import com.scut.common.dto.request.MomentListParam;
import com.scut.common.dto.request.MomentParam;
import com.scut.common.dto.response.MomentDto;
import com.scut.common.response.MultiResponse;
import com.scut.common.response.SingleResponse;
import com.scut.space.service.MomentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;

import static com.scut.common.constant.HttpConstant.USER_ID_HEADER;

@RestController
@RequestMapping("/moment")
@Api(value = "moment", description = "动态")
@Slf4j
public class MomentController {
    @Resource
    private MomentService momentService;

    @PostMapping("/submit")
    @ApiOperation(value = "/submit", notes = "新增动态")
    public SingleResponse<MomentDto> submit(@RequestBody MomentParam momentParam,
                                            @RequestHeader(USER_ID_HEADER) Long userId) {
        MomentDto momentDto = momentService.submit(momentParam, userId);
        if (momentDto == null)
            return new SingleResponse<MomentDto>().unknown(null, "未知错误");
        return new SingleResponse<MomentDto>().success(momentDto);
    }

    @GetMapping("/get")
    @ApiOperation(value = "/get", notes = "获取动态")
    public SingleResponse<MomentDto> get(long id) {
        MomentDto momentDto = momentService.get(id);
        if (momentDto == null)
            return new SingleResponse<MomentDto>().error(null, 2001, "获取某个动态不存在");
        return new SingleResponse<MomentDto>().success(momentDto);
    }

    @GetMapping("/list")
    @ApiOperation(value = "/list", notes = "获取动态列表")
    public MultiResponse<MomentDto> list(MomentListParam momentListParam,
                                         @RequestHeader(USER_ID_HEADER) Long userId) {
        List<MomentDto> result = momentService.getList(momentListParam, userId);
        if (result == null)
            return new MultiResponse<MomentDto>().unknown(null, "未知错误");
        return new MultiResponse<MomentDto>().success(result);
    }

    @GetMapping("/list/{userId}")
    @ApiOperation(value = "/list/{userId}", notes = "获取某个用户的动态列表")
    public MultiResponse<MomentDto> userList(MomentListParam momentListParam,
                                             @PathVariable("userId") long targetUserId) {
        List<MomentDto> result = momentService.getTargetUserList(momentListParam, targetUserId);
        if (result == null)
            return new MultiResponse<MomentDto>().unknown(null, "未知错误");
        return new MultiResponse<MomentDto>().success(result);
    }

    @DeleteMapping("/remove")
    @ApiOperation(value = "/remove", notes = "删除动态")
    public SingleResponse<Boolean> delete(@RequestBody IdParam idParam,
                                          @RequestHeader(USER_ID_HEADER) Long userId) {
        int result = momentService.delete(idParam.getId(), userId);
        if (result == -1)
            return new SingleResponse<Boolean>().error(false, 2003, "删除动态不属于你");
        else if (result == 0)
            return new SingleResponse<Boolean>().error(false, 2002, "删除动态不存在");
        return new SingleResponse<Boolean>().success(true);
    }

    @PostMapping("/like")
    @ApiOperation(value = "/like", notes = "点赞动态")
    public SingleResponse<Boolean> like(@RequestBody IdParam idParam,
                                        @RequestHeader(USER_ID_HEADER) Long userId) {
        int result = momentService.like(idParam.getId(), userId);
        if (result == -1)
            return new SingleResponse<Boolean>().error(false, 2004, "点赞动态不存在");
        else if (result == -2)
            return new SingleResponse<Boolean>().error(false, 2005, "已经点赞过了");
        else if (result == -3)
            return new SingleResponse<Boolean>().unknown(false, "未知错误");
        return new SingleResponse<Boolean>().success(true);
    }

    @DeleteMapping("/unlike")
    @ApiOperation(value = "/unlike", notes = "取消点赞动态")
    public SingleResponse<Boolean> unlike(@RequestBody IdParam idParam,
                                          @RequestHeader(USER_ID_HEADER) Long userId) {
        int result = momentService.unlike(idParam.getId(), userId);
        if (result == -1)
            return new SingleResponse<Boolean>().error(false, 2004, "该动态不存在");
        else if (result == -2)
            return new SingleResponse<Boolean>().error(false, 2005, "未点赞过");
        else if (result == -3)
            return new SingleResponse<Boolean>().unknown(false, "未知错误");
        return new SingleResponse<Boolean>().success(true);
    }

    @GetMapping("/isLike")
    @ApiOperation(value = "/isLike", notes = "是否点赞过动态")
    public SingleResponse<Boolean> isLike(@RequestParam long id,
                                          @RequestHeader(USER_ID_HEADER) Long userId) {
        boolean result = momentService.isLike(id, userId);
        return new SingleResponse<Boolean>().success(result);
    }
}
