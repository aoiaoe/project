package com.geekbang.designpattern._04_observable.my_event_bug;

import java.util.List;
import java.util.concurrent.*;

public class EventBus {

    private Executor executor;
    protected ObserverRegistry registry;
    public EventBus(){
        registry = new ObserverRegistry();
        // 此处可以使用google common包中的MoreExecutors中的DirectExecutorService子类
        this.executor = new ThreadPoolExecutor(1,1,0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1)){
            @Override
            public void execute(Runnable command) {
                command.run();
            }
        };
    }

    public EventBus(Executor executor){
        registry = new ObserverRegistry();
        this.executor = executor;
    }

    public void register(Object observer) {
        registry.register(observer);
    }

    public void post(Object event) {
        List<ObserverAction> methodObserverActions = registry.getMethodObserverActions(event);
        for (ObserverAction action : methodObserverActions) {
            executor.execute(() -> {
                action.execute(event);
            });
        }
    }
}
