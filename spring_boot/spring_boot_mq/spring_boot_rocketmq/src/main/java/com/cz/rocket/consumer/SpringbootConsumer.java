package com.cz.rocket.consumer;

import com.cz.rocket.controller.MqController;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RocketMQMessageListener(consumerGroup = "jzm-group", topic = MqController.TOPIC,consumeThreadMax = 32)
public class SpringbootConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        }catch (Exception e){

        }
        log.info("接收到消息：{}", Thread.currentThread().getName(), s);
    }
}


@Component
@Slf4j
@RocketMQMessageListener(consumerGroup = "jzm-group", topic = MqController.TOPIC,consumeThreadMax = 32)
class SpringbootConsumer2 implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        }catch (Exception e){

        }
        log.info("---->接收到消息：{}", Thread.currentThread().getName(), s);
    }
}
