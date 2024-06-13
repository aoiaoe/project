package com.cz.spring_boot_rabbitmq.topic;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping(value = "/topic")
public class TopicController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping
    public boolean send(int send){
        String s = "message";
        MessageProperties messageProperties = new MessageProperties();
        for (int i = 1; i <= send; i++) {
            Message message = new Message((s + i).getBytes(StandardCharsets.UTF_8), messageProperties);
            rabbitTemplate.convertAndSend("topicExchange", "topicKey", message);
        }
        return true;
    }
}
