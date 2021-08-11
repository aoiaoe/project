package com.cz.spring_boot_rabbitmq.stream;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

//@Component
public class DemoListener {

    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue("demomyMessageExchange.demomyMessageQueue"), exchange = @Exchange(value = "demomyMessageExchange", type = "topic"), key = {"#"})
    })
    public void process(@Payload String message,
                        @Headers Map<String, Object> headers,
                        @Header(value = AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey) {

        System.out.println("接收到消息: " + message);
        System.out.println("headers:");
        headers.entrySet().forEach(System.out::println);

        System.out.println("routingKey: " + routingKey);
    }

}
