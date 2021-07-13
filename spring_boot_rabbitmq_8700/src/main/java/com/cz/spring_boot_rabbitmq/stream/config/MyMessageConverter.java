package com.cz.spring_boot_rabbitmq.stream.config;

import com.cz.spring_boot_rabbitmq.delayqueue.config.TenantDataSourceNameHolder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.AbstractMessageConverter;

import java.nio.charset.StandardCharsets;


public class MyMessageConverter extends AbstractMessageConverter {

    private final String CHARACTER = "__poolName__";
    @Override
    protected boolean supports(Class<?> aClass) {
        return true;
    }


    @Override
    protected Object convertFromInternal(Message<?> message, Class<?> targetClass, Object conversionHint) {
        System.out.println(Thread.currentThread().getName() + "  convertFromInternal消息转换");
        Object payload = message.getPayload();
        if (payload instanceof byte[]) {
            return  (new String((byte[])payload) + CHARACTER + TenantDataSourceNameHolder.get()).getBytes(StandardCharsets.UTF_8);
        }
        else if (payload instanceof String) {
            return  ((String)payload + CHARACTER + TenantDataSourceNameHolder.get()).getBytes(StandardCharsets.UTF_8);
        }
        return payload;
    }

    @Override
    protected Object convertToInternal(Object payload, MessageHeaders headers, Object conversionHint) {
        System.out.println(Thread.currentThread().getName() + "  convertToInternal消息转换");
        if (payload instanceof byte[]) {
            String s = new String((byte[]) payload);
            String[] split = s.split(CHARACTER);
            if(split != null && split[1] != null){
                TenantDataSourceNameHolder.set(split[1]);
            }
            return split != null && split[0] != null ? split[0]: null;
        }
        else if (payload instanceof String) {
            String[] split = ((String) payload).split(CHARACTER);
            if(split != null && split[1] != null){
                TenantDataSourceNameHolder.set(split[1]);
            }
            return split != null && split[0] != null ? split[0] : null;
        }
        else {
            return super.convertToInternal(payload, headers, conversionHint);
        }
    }

}
