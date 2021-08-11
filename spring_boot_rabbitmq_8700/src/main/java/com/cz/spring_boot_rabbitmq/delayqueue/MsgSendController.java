//package com.cz.spring_boot_rabbitmq.delayqueue;
//
//
//import com.cz.spring_boot_rabbitmq.delayqueue.config.TenantDataSourceNameHolder;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.core.MessageProperties;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//
//@Slf4j
//@RestController
//public class MsgSendController {
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    @GetMapping(value = "/send")
//    public boolean send() {
//        MessageProperties messageProperties = new MessageProperties();
//        messageProperties.setExpiration(RabbitMqConfig.DELAY_MILLS + "");
//        TenantDataSourceNameHolder.set(UUID.randomUUID().toString());
//        System.out.println("发送之前Holder: " + TenantDataSourceNameHolder.get());
//        String now = LocalDateTime.now().toString();
//        Message message = new Message(now.getBytes(), messageProperties);
//        this.rabbitTemplate.convertAndSend(RabbitMqConfig.DELAY_TOPIC_EXCHANGE, RabbitMqConfig.KEY, message);
//        log.info("发送消息:{}", now);
//        return true;
//    }
//
//}
