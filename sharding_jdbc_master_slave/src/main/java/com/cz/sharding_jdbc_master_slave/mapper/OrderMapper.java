package com.cz.sharding_jdbc_master_slave.mapper;

import com.cz.sharding_jdbc_master_slave.entity.Order;

public interface OrderMapper {

    Order findOneOrder();

    int insert(Order order);
}
