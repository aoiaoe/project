package com.cz.spring_boot_rabbitmq.stream.config;

import com.cz.spring_boot_rabbitmq.delayqueue.config.TenantDataSourceNameHolder;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;

import java.util.UUID;

@GlobalChannelInterceptor
public class MyStreamChannelInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        System.out.println("线程: " + Thread.currentThread().getName() + "  发送之前查看holder:  " + TenantDataSourceNameHolder.get());
        MessageBuilder.fromMessage(message).setHeader("uuid", TenantDataSourceNameHolder.get());
        return message;
    }

    @Override
    public boolean preReceive(MessageChannel channel) {
        return true;
    }

    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        String uuid = message.getHeaders().get("uuid").toString();
        TenantDataSourceNameHolder.set(uuid);
        System.out.println("线程: " + Thread.currentThread().getName() + "  接受消息获取holder:  " + TenantDataSourceNameHolder.get());
        return message;
    }

    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
        System.out.println("afterReceiveCompletion");
        TenantDataSourceNameHolder.remove();
    }
}
