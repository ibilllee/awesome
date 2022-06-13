package com.scut.board.controller;

import com.scut.common.dto.response.SearchGameListDto;
import com.scut.common.response.MultiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/game")
@RestController
@Api(value = "game")
@Slf4j
public class SearchGameController {
    @GetMapping("/search")
    @ApiOperation(value = "/search", notes = "搜索游戏")
    public MultiResponse<SearchGameListDto> searchGame(@RequestBody String name) {
        return null;
    }
}
