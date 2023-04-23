package com.geekbang.designpattern._04_observable.my_event_bug;

import java.util.concurrent.Executor;

/**
 * @author jzm
 * @date 2023/4/23 : 16:07
 */
public class AsyncEventBus extends EventBus{

    public AsyncEventBus(Executor executor){
        super(executor);
    }
}
