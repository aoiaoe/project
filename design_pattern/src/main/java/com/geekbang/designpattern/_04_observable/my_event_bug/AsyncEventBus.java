package com.geekbang.designpattern._04_observable.my_event_bug;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author jzm
 * @date 2023/4/23 : 16:07
 */
public class AsyncEventBus extends EventBus{

    private Executor executor;
    public AsyncEventBus(){
        super();
        this.executor = new ThreadPoolExecutor(5,10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
    }
    public AsyncEventBus(Executor executor){
        super();
        this.executor = executor;
    }

    @Override
    public void post(Object event) {
        List<ObserverAction> methodObserverActions = registry.getMethodObserverActions(event);
        for (ObserverAction action : methodObserverActions) {
            executor.execute(() -> {
                action.execute(event);
            });
        }
    }
}
