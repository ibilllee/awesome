package com.scut.board.controller;

import com.scut.board.service.GameService;
import com.scut.common.dto.response.GameDetailsDto;
import com.scut.common.dto.response.SearchGameListDto;
import com.scut.common.response.MultiResponse;
import com.scut.common.response.SingleResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RequestMapping("/game")
@RestController
@Api(value = "game")
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
        GameDetailsDto gameDetailsDto = null;
        try {
            gameDetailsDto = gameService.getGameDetails(id);
            return gameDetailsDtoSingleResponse.success(gameDetailsDto);
        } catch (Exception e) {
            return gameDetailsDtoSingleResponse.error(null, 3001, "游戏ID不存在");
        }
    }

    @GetMapping("/search")
    @ApiOperation(value = "/search", notes = "搜索游戏")
    public MultiResponse<SearchGameListDto> searchGame(@RequestParam String name) {
        Collection<SearchGameListDto> searchGameListDtoCollection = null;
        searchGameListDtoCollection = gameService.getGameListBySearch(name);
        return searchGameListDtoMultiResponse.success(searchGameListDtoCollection);
    }

    @GetMapping("/download")
    @ApiOperation(value = "/download", notes = "下载游戏")
    public SingleResponse<String> downloadGame(@RequestParam long id) {
        String downloadUrl = null;
        try {
            downloadUrl = gameService.getGameUrl(id);
            if (downloadUrl.isEmpty()) return stringSingleResponse.error(downloadUrl, 3002, "游戏的下载地址不存在");
            else return stringSingleResponse.success(downloadUrl);
        } catch (Exception e) {
            return stringSingleResponse.error(null, 3001, "游戏ID不存在");
        }
    }
}
