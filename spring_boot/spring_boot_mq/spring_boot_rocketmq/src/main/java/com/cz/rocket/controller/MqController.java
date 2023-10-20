package com.cz.rocket.controller;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MqController {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public static final String TOPIC = "rk-test";

    @GetMapping(value = "/send")
    public boolean sendMsg(String msg) throws Exception{
        msg = msg + "-" + System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            String send = msg + "-" + i;
            rocketMQTemplate.convertAndSend(TOPIC, send);
        }
        return true;
    }

}
