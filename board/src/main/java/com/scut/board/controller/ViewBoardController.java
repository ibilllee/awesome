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

    private MultiResponse<GameForBoardDto> multiResponse;


    @GetMapping("/getHottest")
    @ApiOperation(value = "/getHottest", notes = "最热排行榜")
    public MultiResponse<GameForBoardDto> getHottest() {
        Collection<GameForBoardDto> collection = null;
        try {
            collection = viewBoardService.getViewBoardDataByScore();
            return multiResponse.ok(collection);
        } catch (Exception e) {
            return multiResponse.unprocessable(collection, "3001");
        }
    }

    @GetMapping("/getLatest")
    @ApiOperation(value = "/getLatest", notes = "最新排行榜")
    public MultiResponse<GameForBoardDto> getLatest() {
        Collection<GameForBoardDto> collection = null;
        try {
            collection = viewBoardService.getViewBoardDataByTime();
            return multiResponse.ok(collection);
        } catch (Exception e) {
            return multiResponse.unprocessable(collection, "3001");
        }
    }

}
