package com.cz.orderservice.service;

import com.cz.orderservice.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class OrderService {

    public static final String ORDER = "Order:";

    @Autowired
    private ReactiveRedisTemplate<String, Order> redisOperations;


    public Mono<Order> order(Long orderId) {
        return redisOperations.opsForValue().get(ORDER + orderId);
    }
}
