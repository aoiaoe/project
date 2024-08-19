package com.cz.spring_boot_rabbitmq.topic;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class TopicDemo {

    // 下面这个策略不行
    // 虽然requeue，但是只是给rabbitmq发送一个指令，rabbitmq内部将该消息重新入队，而不是将修改过属性的消息发送给rabbitmq
    // 所以如果想实现，应该修改了消息属性，再发将消息送给rabbitmq
    @RabbitListener(queues = "topicQueue", concurrency = "2", containerFactory = "containerFactory")
    public void onMessage(Message message, Channel channel) throws Exception {
        TimeUnit.SECONDS.sleep(1);
        String msg = new String(message.getBody());
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        if("message1".equals(msg)){
            Object retry = headers.getOrDefault("retry", 0);
            int r = Integer.valueOf(retry.toString()) + 1;
            if(r > 5){
                log.info("达到最大消费次数, 拒收，不入队，进入死信队列,收到消息:{}, headers:{}", msg, headers);
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                return;
            }
            message.getMessageProperties().setHeader("retry", r);
//            headers.put("retry", r);
            log.info("回退,收到消息:{}, headers:{}", msg, headers);
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            return;
        }
        log.info("收到消息:{}, headers:{}", msg, headers);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
