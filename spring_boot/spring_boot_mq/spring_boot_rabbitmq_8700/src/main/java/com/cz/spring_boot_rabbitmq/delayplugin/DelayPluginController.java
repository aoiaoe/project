package com.cz.spring_boot_rabbitmq.delayplugin;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
public class DelayPluginController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 使用rabbitMq延迟消息插件
     * @param second
     * @return
     */
    @GetMapping(value = "/delayPluginMsg")
    public boolean sendDelayMsg(int second){
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("x-delay", second * 1000);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "\"Hello delay plugins !" + second + "秒后见!!!");
        jsonObject.put("date", new Date().toString());
        Message message = new Message(jsonObject.toString().getBytes(StandardCharsets.UTF_8), messageProperties);
        rabbitTemplate.convertAndSend("delayPluginExchange", "delayed", message);
        return true;
    }
}
