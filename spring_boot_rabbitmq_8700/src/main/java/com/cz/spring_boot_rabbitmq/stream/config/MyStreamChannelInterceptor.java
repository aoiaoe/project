package com.cz.spring_boot_rabbitmq.stream.config;

import com.cz.spring_boot_rabbitmq.delayqueue.config.TenantDataSourceNameHolder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;


public class MyStreamChannelInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        TenantDataSourceNameHolder.remove();
        System.out.println(Thread.currentThread().getName() + " --> afterSendCompletion  holder " + TenantDataSourceNameHolder.get());
    }

    @Override
    public boolean preReceive(MessageChannel channel) {
        return true;
    }

    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        return message;
    }

    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
        TenantDataSourceNameHolder.remove();
        System.out.println(Thread.currentThread().getName() + " --> afterReceiveCompletion holder " + TenantDataSourceNameHolder.get());
    }
}
