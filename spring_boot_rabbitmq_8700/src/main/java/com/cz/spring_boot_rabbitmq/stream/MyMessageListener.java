package com.cz.spring_boot_rabbitmq.stream;

import com.cz.spring_boot_rabbitmq.delayqueue.config.TenantDataSourceNameHolder;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component("MyMessageListener")
public class MyMessageListener implements Consumer<String> {

    @Override
    public void accept(String msg) {
        System.out.println("收到消息: " + msg + "  holder:" + TenantDataSourceNameHolder.get());
    }
}
