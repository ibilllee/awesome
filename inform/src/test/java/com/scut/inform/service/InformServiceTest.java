package com.scut.inform.service;

import com.alibaba.fastjson.JSON;
import com.scut.common.constant.MQConstant;
import com.scut.common.dto.response.EmailDto;
import com.scut.common.dto.response.InformDto;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.util.Date;
import java.util.Set;


@SpringBootTest
class InformServiceTest {
    @Resource
    private InformService informService;

//    @Test
//    void addInformToRedis() {
//        InformDto informDto = new InformDto(100,
//                "回复通知1",
//                "你的文章《我是个傻逼》又有一条新回复了，快去看看吧！");
//        informService.addInformToRedis(informDto);
//    }
//
//    @Test
//    void getInformByUserId() {
//        Set<InformDto> informDtos = informService.getInformsByUserId(100);
//        System.out.println(informDtos);
//    }

//    @Resource
//    JavaMailSender javaMailSender;
//
//    @Test
//    void sendEmail() throws Exception {
//        MimeMessage mailMessage = javaMailSender.createMimeMessage();
//        //需要借助Helper类
//        MimeMessageHelper helper = new MimeMessageHelper(mailMessage);
//        String context = "  <h4>您正在<b>Awesome社区</b>找回密码，验证码如下</h4>\n" +
//                "  <center><h1>011001</h1></center>\n" +
//                "  <h4>如果不是您的操作，请忽略</h4>";
//        try {
//            helper.setFrom("ibilllee@foxmail.com");
//            helper.setTo("i.bill.lee@icloud.com");
//            helper.setSubject("【Awesome社区】找回密码-验证码");
//            helper.setSentDate(new Date());//发送时间
//            helper.setText(context, true);
//            //第一个参数要发送的内容，第二个参数是不是Html格式。
//
//            javaMailSender.send(mailMessage);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//    }

//    @Resource
//    private RocketMQTemplate rocketMQTemplate;
//
//    @Test
//    void sendAEmail() {
//        EmailDto emailDto = new EmailDto("i.bill.lee@icloud.com", 123098);
//        rocketMQTemplate.convertAndSend(MQConstant.TOPIC_EMAIL, JSON.toJSONBytes(emailDto));
//
//    }


}