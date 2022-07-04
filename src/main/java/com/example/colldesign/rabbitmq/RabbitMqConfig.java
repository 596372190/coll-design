package com.example.colldesign.rabbitmq;



import com.example.colldesign.comment.service.impl.TbCommentAttachmentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dragon
 * @create 2022-07-04 11:34
 * @description
 */
@Configuration
public class RabbitMqConfig {



    /**
     * 消息的转换器
     * 设置成json 并放入到Spring中
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
