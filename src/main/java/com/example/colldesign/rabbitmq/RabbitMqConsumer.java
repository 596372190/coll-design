package com.example.colldesign.rabbitmq;

import com.example.colldesign.comment.vo.UserVo;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author dragon
 * @create 2022-07-04 11:46
 * @description
 */
@Component

public class RabbitMqConsumer {

    private Logger log = LoggerFactory.getLogger(RabbitMqConsumer.class);

    //注解意思:如果有消息过来 需要消费的时候才会调用该方法

    /**
     * 如果已知传递的参数是 UserTest对象可以通过该注解
     * 消息头需要用map接受
     * 既然是手动接受消息 就需要设置channel
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "userTest-queue", durable = "true"),
            exchange = @Exchange(name = "userTest-exchange", durable = "true", type = "direct"),
            key = "userTest-key"
    )
    )
    @RabbitHandler
    public void receiveUserMessage1(@Payload UserVo userVo, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        try {
            if (userVo.getId().equalsIgnoreCase("0")) {
                throw new Exception();
            }
            System.out.println("userVo1" + userVo);
            Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            //手动接受并告诉rabbitmq消息已经接受了  deliverTag记录接受消息 false不批量接受
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            /*//重新回到队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            System.out.println("尝试重发：" + message.getMessageProperties().getConsumerQueue());*/
            //requeue =true 重回队列，false 丢弃
            channel.basicReject((Long) headers.get(AmqpHeaders.DELIVERY_TAG), false);
            System.out.println("reject");
            // TODO 该消息已经导致异常，重发无意义，自己实现补偿机制
        }
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "userTest-queue", durable = "true"),
            exchange = @Exchange(name = "userTest-exchange", durable = "true", type = "direct"),
            key = "userTest-key"
    )
    )
    @RabbitHandler
    public void receiveUserMessage2(@Payload UserVo userVo, @Headers Map<String, Object> headers, Channel
            channel) throws IOException {
        System.out.println("userVo2" + userVo);
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        //手动接受并告诉rabbitmq消息已经接受了  deliverTag记录接受消息 false不批量接受
        channel.basicAck(deliveryTag, false);
    }
}
