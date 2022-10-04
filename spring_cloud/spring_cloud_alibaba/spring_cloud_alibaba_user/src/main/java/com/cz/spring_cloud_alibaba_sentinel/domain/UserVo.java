package com.cz.spring_cloud_alibaba_sentinel.domain;

import com.cz.spring_cloud_alibaba_api.domain.order.OrderVo;
import lombok.Data;

import java.util.List;

@Data
public class UserVo {

    private Long id;

    private String name;

    private List<OrderVo> orders;
}
