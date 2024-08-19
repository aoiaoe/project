package com.cz.spring_boot_rabbitmq.delayplugin;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class DelayPluginMsgListener {

    /**
     * 对于一条消息，如果我们处理不当，可能会导致一些问题:
     *  1、自动ACK
     *      1、业务代码抛出异常不处理，则会导致消息丢失
     *      2、如果不小心，又写了手动ACK代码，会导致多次ACK，消费者重建
     *  2、手动ACK
     *      a、忘记ACK，消息无法ack -> 消息积压
     *      b、业务处理异常, 且未在try或者finally中处理ACK, 消息无法ack -> 消息积压
     *      c、如果收到消息马上ACK，如果业务处理异常，没有重新发送到mq，或者发送失败，则消息丢失
     *  3、多次ACK，不管合种方式ACK, 会导致消费者channel关闭, 应用重新拉起消费者
     *
     */
    @RabbitListener(queues = "delayPluginQueue")
    public void onMessage(Message message, Channel channel) throws Exception {
        log.info("收到消息:{}, headers:{}", new String(message.getBody()), message.getMessageProperties().getHeaders());
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        TimeUnit.SECONDS.sleep(5);
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        log.info("处理完毕");
        log.info("处理完毕");
//        throw new RuntimeException("就是报错");
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
    }
}
