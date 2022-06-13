package com.scut.board.controller;

import com.scut.common.dto.response.GameDetailsDto;
import com.scut.common.dto.response.SearchGameListDto;
import com.scut.common.response.MultiResponse;
import com.scut.common.response.SingleResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/game")
@RestController
@Api(value = "game")
@Slf4j
public class GameController {
    @GetMapping("/get")
    @ApiOperation(value = "/get", notes = "游戏详情")
    public SingleResponse<GameDetailsDto> getGameDetails(@RequestParam long id) {
        return null;
    }

    @GetMapping("/search")
    @ApiOperation(value = "/search", notes = "搜索游戏")
    public MultiResponse<SearchGameListDto> searchGame(@RequestBody String name) {
        return null;
    }

    @GetMapping("/download")
    @ApiOperation(value = "/download", notes = "下载游戏")
    public SingleResponse<String> downloadGame(@RequestParam long id) {
        return null;
    }
}
