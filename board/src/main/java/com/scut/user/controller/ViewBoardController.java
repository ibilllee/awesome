package com.scut.user.controller;

import com.scut.common.dto.response.GameForBoardDto;
import com.scut.common.response.MultiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/board")
@RestController
@Api(value = "board", description = "排行榜")
@Slf4j
public class ViewBoardController {

    @GetMapping("/getHottest")
    @ApiOperation(value = "/getHottest", notes = "最热排行榜")
    public MultiResponse<GameForBoardDto> getHottest() {
        return null;
    }

    @GetMapping("/getLatest")
    @ApiOperation(value = "/getLatest", notes = "最新排行榜")
    public MultiResponse<GameForBoardDto> getLatest() {
        return null;
    }

}
