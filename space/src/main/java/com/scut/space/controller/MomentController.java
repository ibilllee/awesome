package com.scut.space.controller;

import com.scut.common.dto.request.MomentListParam;
import com.scut.common.dto.request.MomentParam;
import com.scut.common.dto.response.MomentDto;
import com.scut.common.response.MultiResponse;
import com.scut.common.response.SingleResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/moment")
@Api(value = "moment", description = "动态")
@Slf4j
public class MomentController {
    @PostMapping("/submit")
    @ApiOperation(value = "/submit", notes = "新增动态")
    public SingleResponse<MomentDto> submit(@RequestBody MomentParam momentParam) {
        return null;
    }

    @GetMapping("/get")
    @ApiOperation(value = "/get", notes = "获取动态")
    public SingleResponse<MomentDto> get(@RequestParam long id) {
        return null;
    }

    @GetMapping("/list")
    @ApiOperation(value = "/list", notes = "获取动态列表")
    public MultiResponse<MomentDto> list(@RequestParam MomentListParam momentListParam) {
        return null;
    }

    @GetMapping("/myList")
    @ApiOperation(value = "/myList", notes = "获取我的动态列表")
    public MultiResponse<MomentDto> myList(@RequestParam MomentListParam momentListParam) {
        return null;
    }

    @DeleteMapping("/remove")
    @ApiOperation(value = "/remove", notes = "删除动态")
    public SingleResponse<Integer> delete(@RequestBody long id) {
        return null;
    }

    @PostMapping("/like")
    @ApiOperation(value = "/like", notes = "点赞动态")
    public SingleResponse<Integer> like(@RequestBody long id) {
        return null;
    }

    @DeleteMapping("/unlike")
    @ApiOperation(value = "/unlike", notes = "取消点赞动态")
    public SingleResponse<Integer> unlike(@RequestBody long id) {
        return null;
    }
}
