package com.cz.spring_cloud_alibaba_api.domain.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderVo {

    private Long orderId;

    private BigDecimal fee;
}
