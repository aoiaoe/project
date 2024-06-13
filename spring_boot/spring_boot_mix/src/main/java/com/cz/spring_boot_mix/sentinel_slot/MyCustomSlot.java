package com.cz.spring_boot_mix.sentinel_slot;

import com.alibaba.csp.sentinel.context.Context;
import com.alibaba.csp.sentinel.slotchain.AbstractLinkedProcessorSlot;
import com.alibaba.csp.sentinel.slotchain.ResourceWrapper;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class MyCustomSlot extends AbstractLinkedProcessorSlot<Object> {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public void entry(Context context, ResourceWrapper resourceWrapper, Object param, int count, boolean prioritized, Object... args) throws Throwable {
        int res = atomicInteger.incrementAndGet();
        log.info("第{}个进入Sentinel自定义Slot", res);
        fireEntry(context, resourceWrapper, param, count, prioritized, args);
    }

    @Override
    public void exit(Context context, ResourceWrapper resourceWrapper, int count, Object... args) {
        log.info("退出Sentinel自定义Slot");
        fireExit(context, resourceWrapper, count, args);
    }
}
