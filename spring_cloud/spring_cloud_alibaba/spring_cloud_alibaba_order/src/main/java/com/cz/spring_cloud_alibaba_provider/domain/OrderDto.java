package com.cz.spring_cloud_alibaba_provider.domain;

import lombok.Data;

@Data
public class OrderDto {

    private Long userId;

    private Long orderId;

    private Integer fee;
}
