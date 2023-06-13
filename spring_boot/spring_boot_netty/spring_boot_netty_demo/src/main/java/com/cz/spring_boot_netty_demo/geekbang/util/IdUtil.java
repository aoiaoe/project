package com.cz.spring_boot_netty_demo.geekbang.util;

import java.util.concurrent.atomic.AtomicLong;

public class IdUtil {

    private static final AtomicLong IDX = new AtomicLong();

    private IdUtil(){}

    public static long nextId(){
        return IDX.incrementAndGet();
    }
}
