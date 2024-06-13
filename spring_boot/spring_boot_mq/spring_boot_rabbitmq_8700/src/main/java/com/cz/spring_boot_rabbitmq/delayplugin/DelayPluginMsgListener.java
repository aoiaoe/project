package com.cz.spring_boot_rabbitmq.delayplugin;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class DelayPluginMsgListener {

    @RabbitListener(queues = "delayPluginQueue")
    public void onMessage(Message message, Channel channel) throws IOException {
        log.info("收到消息:{}, headers:{}", new String(message.getBody()), message.getMessageProperties().getHeaders());
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
