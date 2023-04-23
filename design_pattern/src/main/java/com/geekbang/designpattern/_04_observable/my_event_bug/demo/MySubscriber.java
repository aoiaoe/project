package com.geekbang.designpattern._04_observable.my_event_bug.demo;

import com.geekbang.designpattern._04_observable.my_event_bug.anno.Subscribe;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jzm
 * @date 2023/4/23 : 16:13
 */
@Slf4j
public class MySubscriber {

    @Subscribe
    public void sub1(MyEvent event){
        log.info("观察者1执行:{}", event);
    }

    @Subscribe
    public void sub2(MyChildEvent event){
        log.info("观察者2执行:{}", event);
    }
}
