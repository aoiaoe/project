package com.cz.spring_boot_rabbitmq.requeue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

@Slf4j
@Configuration
public class RabbitTemplateConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void config(){
        // rabbitmq的生产者确认回调
        // 需配合 spring.rabbitmq.publisher-confirm-type: correlated 使用
        // 可以配合 NavigableMap<Long, String> map = new ConcurrentSkipListMap<>();进行消息重发
        // NavigableMap的接口可以对某一个key之前的数据全部清理，适合批量确认删除

        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("发送消息确认模式结果:消息:{}, 是否成功:{}, 错误原因:{}", correlationData, ack, cause);
        });

        // 路由到queue失败的消息, 路由成功不会回调
        // 需配合 spring.rabbitmq.publisher-returns: true使用
        rabbitTemplate.setReturnsCallback(msg -> {
            log.info("消息发送失败:{}", msg.getMessage());
        });
    }
}
