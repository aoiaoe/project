package com.cz.customerservice.client;

import com.cz.customerservice.domain.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class OrderClient {

    public Mono<Order> getOrderRemote(Long orderId){
        Mono<Order> orderMono = WebClient.create()
                .get()
                .uri("http://127.0.0.1:7000/order/{id}", orderId)
                .retrieve()
                .bodyToMono(Order.class).log("OrderRemote");
        return orderMono;
    }
}
