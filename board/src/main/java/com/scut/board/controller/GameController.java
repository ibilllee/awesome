package com.scut.board.controller;

import com.scut.board.service.GameService;
import com.scut.common.dto.request.BoardCommentParam;
import com.scut.common.dto.request.GameParam;
import com.scut.common.dto.response.GameDetailsDto;
import com.scut.common.dto.response.SearchGameListDto;
import com.scut.common.response.MultiResponse;
import com.scut.common.response.SingleResponse;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/game")
@RestController
@Api(value = "game", description = "游戏相关")
@Slf4j
public class GameController {

    @Autowired
    private GameService gameService;

    private SingleResponse<GameDetailsDto> gameDetailsDtoSingleResponse;
    private MultiResponse<SearchGameListDto> searchGameListDtoMultiResponse;
    private SingleResponse<String> stringSingleResponse;

    @GetMapping("/get")
    @ApiOperation(value = "/get", notes = "游戏详情")
    public SingleResponse<GameDetailsDto> getGameDetails(@RequestParam long id) {
        GameDetailsDto gameDetailsDto;
        gameDetailsDto = gameService.getGameDetails(id);
        if (gameDetailsDto != null) return new SingleResponse<GameDetailsDto>().success(gameDetailsDto);
        else
            return new SingleResponse<GameDetailsDto>().error(null, 3001, "游戏ID不存在");
    }

    @GetMapping("/search")
    @ApiOperation(value = "/search", notes = "搜索游戏")
    public MultiResponse<SearchGameListDto> searchGame(@RequestParam String name) {
        List<SearchGameListDto> searchGameListDtoList;
        searchGameListDtoList = gameService.getGameListBySearch(name);
        return new MultiResponse<SearchGameListDto>().success(searchGameListDtoList);
    }

    @GetMapping("/download")
    @ApiOperation(value = "/download", notes = "下载游戏")
    public SingleResponse<String> downloadGame(@RequestParam long id) {
        String downloadUrl;
        downloadUrl = gameService.getGameUrl(id);
        if (downloadUrl != null) {
            if (downloadUrl.isEmpty()) return new SingleResponse<String>().error(null, 3002, "游戏的下载地址不存在");
            else return new SingleResponse<String>().success(downloadUrl);
        } else return new SingleResponse<String>().error(null, 3001, "游戏的ID不存在");
    }

    @PostMapping("/submit")
    @ApiOperation(value = "/submit", notes = "添加游戏")
    public SingleResponse<GameDetailsDto> addGame(@RequestBody GameParam game) {

        GameDetailsDto result = gameService.addGame(game);
        if (result == null)
            return new SingleResponse<GameDetailsDto>().unknown(null, "未知错误");
        else if (result.getId() == -1L)
            return new SingleResponse<GameDetailsDto>().error(null, 3004, "游戏已存在");
        return new SingleResponse<GameDetailsDto>().success(result);
    }
}
