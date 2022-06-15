package com.scut.inform.service;

import com.scut.common.dto.response.InformDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.Set;


@SpringBootTest
class InformServiceTest {
    @Resource
    private InformService informService;

    @Test
    void addInformToRedis() {
        InformDto informDto = new InformDto(100,
                "回复通知1",
                "你的文章《我是个傻逼》又有一条新回复了，快去看看吧！");
        informService.addInformToRedis(informDto);
    }

    @Test
    void getInformByUserId() {
        Set<InformDto> informDtos = informService.getInformsByUserId(100);
        System.out.println(informDtos);
    }

}