package com.jzm.common.executor;

import com.jzm.common.util.ThreadUtil;

import java.util.concurrent.ThreadPoolExecutor;

public class FutureTaskScheduler {

    static ThreadPoolExecutor executor = null;

    static {
        executor = ThreadUtil.getMixedTargetThreadPool();
    }

    private FutureTaskScheduler(){}

    public static void add(Runnable r){
        executor.submit(r);
    }
}
