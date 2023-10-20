package com.cz.spring_boot_mix.sentinel_slot;

import com.alibaba.csp.sentinel.context.Context;
import com.alibaba.csp.sentinel.slotchain.AbstractLinkedProcessorSlot;
import com.alibaba.csp.sentinel.slotchain.ResourceWrapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyCustomSlot extends AbstractLinkedProcessorSlot<Object> {

    @Override
    public void entry(Context context, ResourceWrapper resourceWrapper, Object param, int count, boolean prioritized, Object... args) throws Throwable {
        log.info("进入Sentinel自定义Slot");
        fireEntry(context, resourceWrapper, param, count, prioritized, args);
    }

    @Override
    public void exit(Context context, ResourceWrapper resourceWrapper, int count, Object... args) {
        log.info("退出Sentinel自定义Slot");
        fireExit(context, resourceWrapper, count, args);
    }
}
