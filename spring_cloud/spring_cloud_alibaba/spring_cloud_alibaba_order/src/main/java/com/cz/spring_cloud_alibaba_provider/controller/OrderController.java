package com.cz.spring_cloud_alibaba_provider.controller;

import com.cz.spring_cloud_alibaba_api.domain.order.OrderVo;
import com.cz.spring_cloud_alibaba_api.facade.order.OrderFacade;
import com.cz.spring_cloud_alibaba_provider.config.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/order")
@RestController
public class OrderController implements OrderFacade {

    @Autowired
    private OrderService orderService;

    @Override
    public List<OrderVo> userOrders(Long userId) {
        return this.orderService.userOrders(userId);
    }
}
