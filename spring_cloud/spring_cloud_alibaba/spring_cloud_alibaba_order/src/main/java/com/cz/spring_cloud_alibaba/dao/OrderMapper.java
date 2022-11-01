package com.cz.spring_cloud_alibaba.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cz.spring_cloud_alibaba.entity.Order;

public interface OrderMapper extends BaseMapper<Order> {

    int createOrder(Integer userId, Integer fee);
}
