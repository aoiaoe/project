package com.cz.spring_cloud_alibaba.service;

import com.cz.spring_cloud_alibaba.config.OrderConfig;
import com.cz.spring_cloud_alibaba.dao.OrderMapper;
import com.cz.spring_cloud_alibaba.domain.order.OrderVo;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Data
@Service
public class OrderService {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
    private static final Integer scale = 2;

    @Autowired
    private OrderConfig orderConfig;
    @Autowired
    private OrderMapper orderMapper;

    public List<OrderVo> userOrders(Long userId){
        if(userId == null){
            return null;
        }
        return Optional.ofNullable(orderConfig.getOrders()).orElse(new ArrayList<>())
                .stream()
                .filter(e -> e.getUserId().equals(userId))
                .map(e -> {
                    OrderVo orderVo = new OrderVo(e.getUserId(), null);
                    if(e.getFee() != null && e.getFee() > 0){
                        final BigDecimal fee = new BigDecimal(e.getFee()).divide(ONE_HUNDRED, scale, BigDecimal.ROUND_HALF_DOWN);
                        orderVo.setFee(fee);
                    }
                    return orderVo;
                })
                .collect(Collectors.toList());
    }

    public boolean createOrder(Integer id, Integer fee, boolean timeOut) {
        log.info("Seata全局事务id=================>{}", RootContext.getXID());
        int res = this.orderMapper.createOrder(id, fee);
        if(timeOut){
            try {
                TimeUnit.SECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return res == 1;
    }
}
