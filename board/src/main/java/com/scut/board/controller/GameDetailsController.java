package com.scut.board.controller;

import com.scut.common.dto.response.GameDto;
import com.scut.common.response.SingleResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/details")
@RestController
@Api(value = "details")
@Slf4j
public class GameDetailsController {
    @GetMapping("/search")
    @ApiOperation(value = "/search", notes = "游戏详情")
    public SingleResponse<GameDto> getGameDetails(@RequestParam long id) {
        return null;
    }

}
