package com.cz.spring_cloud_alibaba.controller;

import com.cz.spring_cloud_alibaba.anntation.IgnoreCommonResponseBody;
import com.cz.spring_cloud_alibaba.domain.order.OrderVo;
import com.cz.spring_cloud_alibaba_api.facade.order.OrderFacade;
import com.cz.spring_cloud_alibaba.config.OrderService;
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

    @IgnoreCommonResponseBody
    @Override
    public List<OrderVo> userOrders(Long userId) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        final String client = request.getHeader("client");
        log.info("[客户端]:{}", client);
        return this.orderService.userOrders(userId);
    }

    @IgnoreCommonResponseBody
    @Override
    public boolean createOrder(Integer id, Integer fee) {
        return this.orderService.createOrder(id, fee);
    }
}
