package com.scut.inform.service;

//import org.apache.rocketmq.spring.core.RocketMQTemplate;

import com.scut.common.constant.RedisConstant;
import com.scut.common.dto.response.EmailDto;
import com.scut.common.dto.response.InformDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class InformService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    JavaMailSender javaMailSender;

    public void addInformToRedis(InformDto message) {
        String setName = RedisConstant.REDIS_SET_INFORM + "$" + message.getUserId();
        redisTemplate.opsForSet().add(setName, message);
    }

    public Set<InformDto> getInformsByUserId(long userId) {
        Set<InformDto> members = redisTemplate.opsForSet().members(RedisConstant.REDIS_SET_INFORM + "$" + userId);
        redisTemplate.delete(RedisConstant.REDIS_SET_INFORM + "$" + userId);
        return members;
    }

    public void sendEmail(EmailDto emailDto) {
        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        //需要借助Helper类
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage);
        String context = "  <h4>您正在<b>Awesome社区</b>找回密码，验证码如下</h4>\n" +
                "  <center><h1>" + emailDto.getCode() + "</h1></center>\n" +
                "  <h4>如果不是您的操作，请忽略</h4>";
        try {
            helper.setFrom("ibilllee@foxmail.com");
            helper.setTo(emailDto.getEmail());
            helper.setSubject("【Awesome社区】找回密码-验证码");
            helper.setSentDate(new Date());//发送时间
            helper.setText(context, true);//第一个参数要发送的内容，第二个参数是不是Html格式。

            javaMailSender.send(mailMessage);
            log.info("邮件发送成功 to:{} code:{}", emailDto.getEmail(), emailDto.getCode());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
