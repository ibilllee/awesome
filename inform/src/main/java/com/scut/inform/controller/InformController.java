package com.scut.inform.controller;

import com.alibaba.fastjson.JSON;
import com.scut.common.constant.MQConstant;
import com.scut.common.dto.response.InformDto;
import com.scut.common.response.MultiResponse;
import com.scut.inform.service.InformService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.scut.common.constant.HttpConstant.USER_ID_HEADER;

@RestController
@RequestMapping("/inform")
@Slf4j
public class InformController {
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Resource
    private InformService informService;

    @GetMapping("/test")
    @ApiOperation(value = "/test", notes = "测试接口-新增信息")
    public String test(@RequestHeader(USER_ID_HEADER) long userId){
        InformDto inform = new InformDto(userId,
                "回复通知",
                "你的文章《我是个傻逼》又有一条新回复了，快去看看吧！");
        rocketMQTemplate.convertAndSend(MQConstant.TOPIC_PUSH_INFORM, JSON.toJSONBytes(inform));
        return inform.toString();
    }

    @GetMapping("/list")
    @ApiOperation(value = "/list", notes = "获得用户通知")
    public MultiResponse<InformDto> list(@RequestHeader(USER_ID_HEADER) long userId){
        return new MultiResponse<InformDto>().success(informService.getInformsByUserId(userId));
    }

}
