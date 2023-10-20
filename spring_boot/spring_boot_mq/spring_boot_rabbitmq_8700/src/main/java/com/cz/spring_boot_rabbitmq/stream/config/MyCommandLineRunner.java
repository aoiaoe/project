package com.cz.spring_boot_rabbitmq.stream.config;

import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.stream.messaging.DirectWithAttributesChannel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MyCommandLineRunner implements CommandLineRunner, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void run(String... args) throws Exception {
//        MyStreamChannelInterceptor interceptor = new MyStreamChannelInterceptor();
//        Map<String, DirectWithAttributesChannel> beansOfType = applicationContext.getBeansOfType(DirectWithAttributesChannel.class);
//        for (Map.Entry<String, DirectWithAttributesChannel> entry : beansOfType.entrySet()) {
//            entry.getValue().addInterceptor(interceptor);
//        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
