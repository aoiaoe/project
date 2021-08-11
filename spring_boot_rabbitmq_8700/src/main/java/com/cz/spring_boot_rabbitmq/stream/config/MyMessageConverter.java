//package com.cz.spring_boot_rabbitmq.stream.config;
//
//import com.alibaba.fastjson.JSON;
//import com.cz.spring_boot_rabbitmq.delayqueue.config.TenantDataSourceNameHolder;
//import com.cz.spring_boot_rabbitmq.stream.OutterData;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageHeaders;
//import org.springframework.messaging.converter.AbstractMessageConverter;
//import org.springframework.messaging.support.MessageBuilder;
//
//import java.nio.charset.StandardCharsets;
//
//
//public class MyMessageConverter extends AbstractMessageConverter {
//
//    private final String CHARACTER = "__poolName__";
//    @Override
//    protected boolean supports(Class<?> aClass) {
//        return true;
//    }
//
//    @Override
//    protected Object convertFromInternal(Message<?> message, Class<?> targetClass, Object conversionHint) {
//        message = MessageBuilder.fromMessage(message).setHeader("UUID", TenantDataSourceNameHolder.get()).build();
//        return super.convertFromInternal(message, targetClass, conversionHint);
//    }
//
//    @Override
//    protected Object convertToInternal(Object payload, MessageHeaders headers, Object conversionHint) {
//        return super.convertToInternal(payload, headers, conversionHint);
//    }
//}
