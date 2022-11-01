package com.cz.spring_cloud_alibaba_api.facade.order;

import com.cz.spring_cloud_alibaba.domain.order.OrderVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface OrderFacade {

    @GetMapping(value = "/userOrders")
    List<OrderVo> userOrders(@RequestParam(value = "userId") Long userId);

    @PostMapping(value = "/createOrder")
    boolean createOrder(@RequestParam("id") Integer id,
                    @RequestParam("fee") Integer fee);
}
