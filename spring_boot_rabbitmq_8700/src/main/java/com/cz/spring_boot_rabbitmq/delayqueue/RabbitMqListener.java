package com.cz.spring_boot_rabbitmq.delayqueue;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMqListener {

    @RabbitListener(queues = {RabbitMqConfig.PROCESS_QUEUE}, containerFactory = "containerFactory")
    public void process(Channel channel, Message message) {
        String msg = new String(message.getBody());
        log.info("接受消息:{}", msg);
    }
}
