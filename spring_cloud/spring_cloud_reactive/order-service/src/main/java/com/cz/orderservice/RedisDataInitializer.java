package com.cz.orderservice;

import com.cz.orderservice.domain.Order;
import com.cz.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class RedisDataInitializer implements CommandLineRunner {

    @Autowired
    private ReactiveRedisTemplate<String, Order> redisOperations;

    @Override
    public void run(String... args) throws Exception {
        ReactiveValueOperations<String, Order> ops = redisOperations.opsForValue();
        for (Order order : orderList()) {
            ops.set(OrderService.ORDER + order.getId(), order).subscribe();
           log.info("---");
        }
    }

    private List<Order> orderList(){
        return new ArrayList<Order>(){{
            add(new Order(5L, "张三2", 102));
            add(new Order(6L, "李四2", 102));
        }};
    }
}
