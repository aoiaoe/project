package com.geekbang.designpattern._04_observable.my_event_bug.demo;

import com.geekbang.designpattern._04_observable.my_event_bug.AsyncEventBus;
import com.geekbang.designpattern._04_observable.my_event_bug.EventBus;

/**
 * @author jzm
 * @date 2023/4/23 : 16:10
 */
public class MyEventBusDemo {

    public static void main(String[] args) {

        EventBus eventBus = new EventBus();
        eventBus = new AsyncEventBus();
        eventBus.register(new MySubscriber());
        eventBus.post(new MyEvent("测试"));
        eventBus.post(new MyChildEvent("子事件测试"));
    }
}
