package com.cz.rocket.controller;

import com.cz.rocket.entity.Order;
import com.cz.rocket.service.OrderService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MqController {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

//    public static final String TOPIC = "rk-test2";
    public static final String TOPIC = "rk-test-5part";

    @GetMapping(value = "/send")
    public boolean sendMsg(String msg) throws Exception{
        msg = msg + "-" + System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            String send = msg + "-" + i;
            rocketMQTemplate.convertAndSend(TOPIC, send);
        }
        return true;
    }

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/order")
    public boolean createOrder(Integer amount, String desc){
        Order order = new Order();
        order.setAmount(amount);
        order.setDescription(desc);
        return this.orderService.createOrder(order);
    }

}
