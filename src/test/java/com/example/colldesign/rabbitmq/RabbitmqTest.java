package com.example.colldesign.rabbitmq;

import cn.hutool.core.date.DateUtil;
import com.example.colldesign.comment.vo.UserVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author dragon
 * @create 2022-07-04 11:22
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void test001() {
        //1.消息推送到server 找不到交换机 ConfirmCallback ack：false
        //2.消息推送到server 找到交换机 找不到queue  ConfirmCallback ack：false  returnCallback
        //3.消息推送成功 ConfirmCallback ack：true
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if(!ack) {
                System.out.println("消息发送失败。" + "原因：" + cause);
            }
        });

        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            System.out.println("ReturnCallback:     " + "消息：" + message);
            System.out.println("ReturnCallback:     " + "回应码：" + replyCode);
            System.out.println("ReturnCallback:     " + "回应信息：" + replyText);
            System.out.println("ReturnCallback:     " + "交换机：" + exchange);
            System.out.println("ReturnCallback:     " + "路由键：" + routingKey);
        });
        for (int i = 0; i < 1; i++) {
            UserVo userVo = UserVo.builder().id(i+"").name("testRabbitmq").company("yeap"+i).lastLoginTime(DateUtil.date()).build();
            if(i==47){
                rabbitTemplate.convertAndSend("nnnone", "userTest-key", userVo);
                continue;
            }else if(i==49){
                rabbitTemplate.convertAndSend("userTest-exchange", "nnnone", userVo);
                continue;
            }
            rabbitTemplate.convertAndSend("userTest-exchange", "userTest-key", userVo);
            System.out.println("ok:"+i);
        }
    }
}
