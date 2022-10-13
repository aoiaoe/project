package com.cz.spring_cloud_alibaba.domain;

import com.cz.spring_cloud_alibaba.domain.order.OrderVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserVo {

    private Long id;

    private String name;

    private List<OrderVo> orders;
}
