package com.cz.spring_boot_rabbitmq.stream.config;

import com.cz.spring_boot_rabbitmq.delayqueue.config.TenantDataSourceNameHolder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;

import java.util.UUID;


public class MyStreamChannelInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        message = MessageBuilder.fromMessage(message).setHeader("UUID", UUID.randomUUID().toString()).build();
        if(message.getHeaders().containsKey("UUID")){
            TenantDataSourceNameHolder.set(message.getHeaders().get("poolName", String.class));
        }
        System.out.println("preSend:"+ TenantDataSourceNameHolder.get());
        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        System.out.println("postSend");
    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        System.out.println("afterSendCompletion");
        TenantDataSourceNameHolder.remove();
        System.out.println(Thread.currentThread().getName() + " --> afterSendCompletion  holder " + TenantDataSourceNameHolder.get());
    }

    @Override
    public boolean preReceive(MessageChannel channel) {
        System.out.println("preReceive");
        return true;
    }

    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        System.out.println("postReceive");
        return message;
    }

    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
        System.out.println("afterReceiveCompletion");
        TenantDataSourceNameHolder.remove();
        System.out.println(Thread.currentThread().getName() + " --> afterReceiveCompletion holder " + TenantDataSourceNameHolder.get());
    }
}
