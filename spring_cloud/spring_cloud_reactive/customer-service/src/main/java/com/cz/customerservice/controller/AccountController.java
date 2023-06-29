package com.cz.customerservice.controller;

import com.cz.customerservice.domain.Order;
import com.cz.customerservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AccountController {

    private static final String CONS = "sd";

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/order/{orderId}")
    public Mono<Order> accountInfo(@PathVariable Long orderId){
        int number = 1;
        number++;
        System.out.println(number);
        return orderService.account(orderId);
    }
}
