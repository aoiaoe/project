package com.cz.sharding_jdbc_master_slave;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = {"com.cz.sharding_jdbc_master_slave.mapper"})
@SpringBootApplication
public class ShardingJdbcMasterSlaveApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingJdbcMasterSlaveApplication.class, args);
    }

}
