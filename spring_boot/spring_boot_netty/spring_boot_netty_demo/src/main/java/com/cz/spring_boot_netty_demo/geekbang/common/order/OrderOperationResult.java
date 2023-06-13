package com.cz.spring_boot_netty_demo.geekbang.common.order;

import com.cz.spring_boot_netty_demo.geekbang.common.OperationResult;
import lombok.Data;

@Data
public class OrderOperationResult extends OperationResult {

    private final int tableId;
    private final String dish;
    private final boolean complete;
}
