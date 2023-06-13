package com.cz.spring_boot_netty_demo.geekbang.common.order;

import com.cz.spring_boot_netty_demo.geekbang.common.Operation;
import com.cz.spring_boot_netty_demo.geekbang.common.OperationResult;
import com.google.common.util.concurrent.Uninterruptibles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
@AllArgsConstructor
@Data
public class OrderOperation extends Operation {

    private int tableId;
    private String dish;

    @Override
    public OperationResult execute() {
        log.info("order's executing startup with orderRequest:{}", toString());
        // 模拟业务靠逻辑处理
        Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
        log.info("order's executing complete!!");
        return new OrderOperationResult(tableId, dish, true);
    }
}
