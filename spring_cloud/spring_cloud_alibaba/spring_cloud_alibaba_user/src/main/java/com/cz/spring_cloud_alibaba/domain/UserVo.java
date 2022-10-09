package com.cz.spring_cloud_alibaba.domain;

import com.cz.spring_cloud_alibaba.domain.order.OrderVo;
import lombok.Data;

import java.util.List;

@Data
public class UserVo {

    private Long id;

    private String name;

    private List<OrderVo> orders;
}
