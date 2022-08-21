package com.cz.spring_boot_rabbitmq.stream;

import com.cz.spring_boot_rabbitmq.delayqueue.config.TenantDataSourceNameHolder;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component("demoMyMessageListenerLong")
public class MyMessageListener2 implements Consumer<Long> {

    @Override
    public void accept(Long msg) {
        System.out.println(Thread.currentThread().getName() + "收到消息: " + msg + "  holder:" + TenantDataSourceNameHolder.get());
    }
}