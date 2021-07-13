package com.cz.spring_boot_rabbitmq.stream.config;

import org.springframework.cloud.stream.binding.CompositeMessageChannelConfigurer;
import org.springframework.cloud.stream.binding.MessageChannelConfigurer;
import org.springframework.cloud.stream.binding.SubscribableChannelBindingTargetFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.util.List;

@Configuration
public class StreamRabbitConfig {

    @Bean
    public MyMessageConverter myMessageConverter(){
        return new MyMessageConverter();
    }

//    @Bean
//    public MyMessageConverterConfigurer myMessageConverterConfigurer(SubscribableChannelBindingTargetFactory factory) throws NoSuchFieldException, IllegalAccessException {
//        Class<? extends SubscribableChannelBindingTargetFactory> aClass = factory.getClass();
//        Field messageChannelConfigurer = aClass.getDeclaredField("messageChannelConfigurer");
//        messageChannelConfigurer.setAccessible(true);
//        Object o = messageChannelConfigurer.get(factory);
//        CompositeMessageChannelConfigurer configurer = (CompositeMessageChannelConfigurer) o;
//        Class<? extends CompositeMessageChannelConfigurer> aClass1 = configurer.getClass();
//        Field messageChannelConfigurers = aClass1.getDeclaredField("messageChannelConfigurers");
//        messageChannelConfigurers.setAccessible(true);
//        Object o1 = messageChannelConfigurers.get(configurer);
//        List<MessageChannelConfigurer> o11 = (List<MessageChannelConfigurer>) o1;
//        MyMessageConverterConfigurer myMessageConverterConfigurer = new MyMessageConverterConfigurer();
//        o11.add(myMessageConverterConfigurer);
//        return myMessageConverterConfigurer;
//    }
}
