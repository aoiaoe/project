package com.cz.rocket.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RocketMQMessageListener(consumerGroup = "OrderTxConsumeGroup", topic = "OrderTxTopic", consumeThreadMax = 10)
public class OrderTxMsgListener implements RocketMQListener<Long> {


    @Override
    public void onMessage(Long s) {
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("接收到事务消息：{}-{}", Thread.currentThread().getName(), s);
    }
}
