package com.cz.spring_boot_mix.guava.eventbus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author jzm
 * @date 2022/6/28 : 09:51
 */
@Slf4j
public class EventBusTest {

    /**
     * 同步消费
     * @throws InterruptedException
     */
    @Test
    public void testConsumer() throws InterruptedException {
        EventBus eventBus = new EventBus();

        eventBus.register(new Event1Consumer());
        eventBus.post(new Event1("ceshi"));
    }

    /**
     * 异步事件消费
     * @throws InterruptedException
     */
    @Test
    public void testAsyncConsumer() throws InterruptedException {
        EventBus eventBus = new AsyncEventBus(Executors.newSingleThreadExecutor(), (throwable, context) -> {
            log.error("消费事件出错 ： ()", throwable);
        });

        eventBus.register(new Event1Consumer());
        eventBus.post(new Event1("ceshi"));

        TimeUnit.SECONDS.sleep(1);
    }
}
