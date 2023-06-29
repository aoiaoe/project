package com.cz.orderservice.controller;
import com.cz.orderservice.domain.Order;
import com.cz.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/order/{orderId}")
    public Mono<Order> accountInfo(@PathVariable Long orderId){
        return orderService.order(orderId).defaultIfEmpty(new Order(0L, null, 0));
    }
}
