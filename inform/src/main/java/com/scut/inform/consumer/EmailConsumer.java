package com.scut.inform.consumer;

import com.alibaba.fastjson.JSON;
import com.scut.common.constant.MQConstant;
import com.scut.common.dto.response.EmailDto;
import com.scut.common.dto.response.InformDto;
import com.scut.inform.service.InformService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@RocketMQMessageListener(topic = MQConstant.TOPIC_EMAIL,
        consumerGroup = "email_consumer_group")
@Slf4j
public class EmailConsumer implements RocketMQListener<String> {
    @Resource
    private InformService informService;

    @Override
    public void onMessage(String message) {
        log.info(MQConstant.TOPIC_EMAIL + " 接到消息:" + message);
        EmailDto emailDto = JSON.parseObject(message, EmailDto.class);
        informService.sendEmail(emailDto);
    }
}
