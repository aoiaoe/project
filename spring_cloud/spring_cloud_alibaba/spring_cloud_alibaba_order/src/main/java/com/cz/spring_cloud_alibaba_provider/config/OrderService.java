package com.cz.spring_cloud_alibaba_provider.config;

import com.cz.spring_cloud_alibaba_api.domain.order.OrderVo;
import com.cz.spring_cloud_alibaba_provider.domain.OrderDto;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Service
public class OrderService {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
    private static final Integer scale = 2;

    @Autowired
    private OrderConfig orderConfig;

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
}
