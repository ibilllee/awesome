package com.scut.inform.consumer;

import com.alibaba.fastjson.JSON;
import com.scut.common.constant.MQConstant;
import com.scut.common.dto.response.InformDto;
import com.scut.inform.service.InformService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@RocketMQMessageListener(topic = MQConstant.TOPIC_PUSH_INFORM,
        consumerGroup = "inform_consumer_group")
@Slf4j
public class PushInFormConsumer implements RocketMQListener<String> {
    @Resource
    private InformService informService;

    @Override
    public void onMessage(String message) {
        log.info(MQConstant.TOPIC_PUSH_INFORM + " 接到消息:" + message);
        InformDto informDto = JSON.parseObject(message, InformDto.class);
        informService.addInformToRedis(informDto);
        System.out.println(informDto);
    }
}

