package com.cz.service.controller;

import com.cz.service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/order")
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 在linux中可以使用wrk工具进行压测
     * @param productId
     * @return
     */
    @GetMapping(value = "/create")
    public boolean createOrder(String productId){
        return orderService.createOrder(productId);
    }
}
