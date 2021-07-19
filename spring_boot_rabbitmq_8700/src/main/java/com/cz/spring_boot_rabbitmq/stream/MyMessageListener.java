package com.cz.spring_boot_rabbitmq.stream;

import com.cz.spring_boot_rabbitmq.delayqueue.config.TenantDataSourceNameHolder;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component("demoMyMessageListener")
public class MyMessageListener implements Consumer<Data> {

    @Override
    public void accept(Data msg) {
        System.out.println(Thread.currentThread().getName() + "收到消息: " + msg + "  holder:" + TenantDataSourceNameHolder.get());
        Long.parseLong(msg.getName());
    }
}
