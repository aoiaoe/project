package com.cz.spring_boot_netty_demo.geekbang.common.keepalive;

import com.cz.spring_boot_netty_demo.geekbang.common.OperationResult;
import lombok.Data;

@Data
public class KeepAliveOperationResult extends OperationResult {

    private final long time;
}
