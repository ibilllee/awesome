package com.scut.board.controller;

import com.scut.board.service.ViewBoardService;
import com.scut.common.dto.response.GameForBoardDto;
import com.scut.common.response.MultiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RequestMapping("/board")
@RestController
@Api(value = "board", description = "排行榜")
@Slf4j
public class ViewBoardController {

    @Autowired
    private ViewBoardService viewBoardService;

    private MultiResponse<GameForBoardDto> gameForBoardDtoMultiResponse;

    @GetMapping("/getHottest")
    @ApiOperation(value = "/getHottest", notes = "最热排行榜")
    public MultiResponse<GameForBoardDto> getHottest() {
        Collection<GameForBoardDto> gameForBoardDtoCollection = null;
        try {
            gameForBoardDtoCollection = viewBoardService.getViewBoardDataByScore();
            return gameForBoardDtoMultiResponse.ok(gameForBoardDtoCollection);
        } catch (Exception e) {
            return gameForBoardDtoMultiResponse.unprocessable(gameForBoardDtoCollection, "3001");
        }
    }

    @GetMapping("/getLatest")
    @ApiOperation(value = "/getLatest", notes = "最新排行榜")
    public MultiResponse<GameForBoardDto> getLatest() {
        Collection<GameForBoardDto> gameForBoardDtoCollection = null;
        try {
            gameForBoardDtoCollection = viewBoardService.getViewBoardDataByTime();
            return gameForBoardDtoMultiResponse.ok(gameForBoardDtoCollection);
        } catch (Exception e) {
            return gameForBoardDtoMultiResponse.unprocessable(gameForBoardDtoCollection, "3001");
        }
    }

}
