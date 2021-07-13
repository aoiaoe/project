package com.cz.spring_boot_rabbitmq.stream;

import com.cz.spring_boot_rabbitmq.delayqueue.config.TenantDataSourceNameHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binding.CompositeMessageChannelConfigurer;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RequestMapping(value = "/stream")
@RestController
public class MyStreamMessageController {

    @Autowired
    private StreamBridge streamBridge;

    @Autowired
    private CompositeMessageChannelConfigurer integrationArgumentResolverMessageConverter;

    @RequestMapping(value = "/send")
    public boolean send(String msg){
        TenantDataSourceNameHolder.set(UUID.randomUUID().toString());
        System.out.println("线程名: " + Thread.currentThread().getName() + "controller发送设置holder: " + TenantDataSourceNameHolder.get());
        streamBridge.send("myMessageExchange", msg);
        System.out.println("发送消息: " + msg);
        return true;
    }
}
