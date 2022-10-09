package com.cz.spring_cloud_alibaba.domain;

import lombok.Data;

@Data
public class OrderDto {

    private Long userId;

    private Long orderId;

    private Integer fee;
}
