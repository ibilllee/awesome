package com.scut.board.controller;

import com.scut.common.response.SingleResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/download")
@RestController
@Api(value = "download")
@Slf4j
public class DownloadController {
    @GetMapping("/get")
    @ApiOperation(value = "/get", notes = "下载游戏")
    public SingleResponse<String> getDownloadUrl(@RequestParam long id) {
        return null;
    }
}
