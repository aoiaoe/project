package com.cz.spring_boot_netty_demo.geekbang.common.keepalive;

import com.cz.spring_boot_netty_demo.geekbang.common.Operation;
import com.cz.spring_boot_netty_demo.geekbang.common.OperationResult;
import lombok.Data;
import lombok.extern.java.Log;

@Log
@Data
public class KeepAliveOperation extends Operation {

    private long time;

    public KeepAliveOperation(){
        this.time = System.nanoTime();
    }

    @Override
    public OperationResult execute() {
        return new KeepAliveOperationResult(time);
    }
}
