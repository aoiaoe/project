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
//public class MyMessageConverter1 extends AbstractMessageConverter {
//
//    private final String CHARACTER = "__poolName__";
//    @Override
//    protected boolean supports(Class<?> aClass) {
//        return true;
//    }
//
//    @Override
//    protected Object convertFromInternal(Message<?> message, Class<?> targetClass, Object conversionHint) {
//        System.out.println(Thread.currentThread().getName() + "  convertFromInternal 消息转换");
//        Object payload = message.getPayload();
//        if (payload instanceof byte[]) {
//            String p = new String((byte[]) payload);
//            if (p.contains(CHARACTER)) {
//                String[] split = p.split(CHARACTER);
//                if (split != null && split[1] != null) {
//                    TenantDataSourceNameHolder.set(split[1]);
//                }
//                return split != null && split[0] != null ? split[0].getBytes(StandardCharsets.UTF_8) : null;
//            } else {
//                OutterData outterData = JSON.parseObject(p, OutterData.class);
//                TenantDataSourceNameHolder.set(outterData.getUuid());
//                return outterData.getData();
//            }
//        } else if(payload instanceof OutterData) {
//            return ((OutterData)payload).getData();
//        }
//        return super.convertFromInternal(message, targetClass, conversionHint);
//    }
//
//    @Override
//    protected Object convertToInternal(Object payload, MessageHeaders headers, Object conversionHint) {
//        System.out.println(Thread.currentThread().getName() + "  convertToInternal 消息转换");
//        if (payload instanceof byte[]) {
//            String s = new String((byte[]) payload);
//            if(s.contains(CHARACTER)) {
//                String[] split = s.split(CHARACTER);
//                if (split != null && split[1] != null) {
//                    TenantDataSourceNameHolder.set(split[1]);
//                }
//                return split != null && split[0] != null ? split[0] : null;
//            } else {
//                s = s + CHARACTER + TenantDataSourceNameHolder.get();
//                return s.getBytes(StandardCharsets.UTF_8);
//            }
//        }
////        else if (payload instanceof String) {
////            if(((String)payload).contains(CHARACTER)) {
////                String[] split = ((String) payload).split(CHARACTER);
////                if (split != null && split[1] != null) {
////                    TenantDataSourceNameHolder.set(split[1]);
////                }
////                return split != null && split[0] != null ? split[0] : null;
////            } else {
////                return payload + CHARACTER + TenantDataSourceNameHolder.get();
////            }
////        } else if(payload instanceof Object){
//        else {
//            OutterData build = new OutterData();
//            build.setData(payload);
//            build.setUuid(TenantDataSourceNameHolder.get());
//            return JSON.toJSONString(build).getBytes(StandardCharsets.UTF_8);
////            return build.toString().getBytes(StandardCharsets.UTF_8);
//        }
////        return super.convertToInternal(payload, headers, conversionHint);
//    }
//
//}
