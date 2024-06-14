package com.jzm.common.executor;

import com.google.common.util.concurrent.*;
import com.jzm.common.util.ThreadUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public class CallbackTaskScheduler {

    static ListeningExecutorService guavaPool = null;

    static {
        ExecutorService pool = ThreadUtil.getMixedTargetThreadPool();
        guavaPool = MoreExecutors.listeningDecorator(pool);
    }

    public static <R> void add(CallableTask<R> task){
        ListenableFuture<R> future = guavaPool.submit(new Callable<R>() {
            @Override
            public R call() throws Exception {
                R r = task.execute();
                return r;
            }
        });

        Futures.addCallback(future, new FutureCallback<R>() {
            @Override
            public void onSuccess(R r) {
                task.success(r);
            }

            @Override
            public void onFailure(Throwable throwable) {
                task.fail(throwable);
            }
        });
    }
}
