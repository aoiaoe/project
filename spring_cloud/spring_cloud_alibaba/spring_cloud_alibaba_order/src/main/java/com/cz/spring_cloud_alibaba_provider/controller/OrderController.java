package com.cz.spring_cloud_alibaba_provider.controller;

import com.cz.spring_cloud_alibaba_api.domain.order.OrderVo;
import com.cz.spring_cloud_alibaba_api.facade.order.OrderFacade;
import com.cz.spring_cloud_alibaba_provider.config.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RequestMapping(value = "/order")
@RestController
public class OrderController implements OrderFacade {

    @Autowired
    private OrderService orderService;

    @Override
    public List<OrderVo> userOrders(Long userId) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        final String client = request.getHeader("client");
        log.info("[客户端]:{}", client);
        return this.orderService.userOrders(userId);
    }
}
