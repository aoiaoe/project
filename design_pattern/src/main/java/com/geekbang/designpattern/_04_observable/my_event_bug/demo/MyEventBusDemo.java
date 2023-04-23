package com.geekbang.designpattern._04_observable.my_event_bug.demo;

import com.geekbang.designpattern._04_observable.my_event_bug.AsyncEventBus;
import com.geekbang.designpattern._04_observable.my_event_bug.EventBus;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author jzm
 * @date 2023/4/23 : 16:10
 */
public class MyEventBusDemo {

    public static void main(String[] args) {

        EventBus eventBus = new EventBus();
        // 异步事件
//        eventBus = new AsyncEventBus(new ThreadPoolExecutor(5,5, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10)));
        eventBus.register(new MySubscriber());
        eventBus.post(new MyEvent("测试"));
        eventBus.post(new MyChildEvent("子事件测试"));
    }
}
