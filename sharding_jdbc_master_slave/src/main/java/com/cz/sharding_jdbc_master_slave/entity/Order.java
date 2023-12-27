package com.cz.sharding_jdbc_master_slave.entity;

import lombok.Data;

@Data
public class Order {

    private Long id;
    private Long companyId;
    private String status;
}
