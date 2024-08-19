package com.cz.customerservice.service;

import com.cz.customerservice.client.OrderClient;
import com.cz.customerservice.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

@Service
public class OrderService {

    @Autowired
    private OrderClient client;

    public Mono<Order> account(Long orderId){
        return client.getOrderRemote(orderId);
    }
}
