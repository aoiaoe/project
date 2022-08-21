package com.cz.spring_boot_mix.guava.eventbus;

import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jzm
 * @date 2022/6/28 : 09:50
 */
@Slf4j
public class Event1Consumer {

    @Subscribe
    public void consumer(Event1 event1){
        log.info("event1 : {}", event1);
    }

}
