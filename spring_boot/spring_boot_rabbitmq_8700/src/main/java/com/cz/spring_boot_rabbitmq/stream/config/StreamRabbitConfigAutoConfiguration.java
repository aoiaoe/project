package com.cz.spring_boot_rabbitmq.stream.config;

import com.cz.spring_boot_rabbitmq.delayqueue.config.TenantDataSourceNameHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.stream.binder.rabbit.RabbitMessageChannelBinder;
import org.springframework.cloud.stream.binder.rabbit.config.RabbitMessageChannelBinderConfiguration;
import org.springframework.cloud.stream.binding.*;
import org.springframework.cloud.stream.config.BindingProperties;
import org.springframework.cloud.stream.config.BindingServiceConfiguration;
import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.cloud.stream.config.ProducerMessageHandlerCustomizer;
import org.springframework.cloud.stream.messaging.DirectWithAttributesChannel;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint;
import org.springframework.integration.amqp.support.AmqpHeaderMapper;
import org.springframework.integration.channel.AbstractMessageChannel;
import org.springframework.integration.channel.interceptor.GlobalChannelInterceptorWrapper;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AutoConfigureBefore(value = RabbitMessageChannelBinderConfiguration.class)
@Configuration
public class StreamRabbitConfigAutoConfiguration implements ApplicationContextAware {

    @Autowired
    private BindingServiceProperties properties;
    @Autowired
    private BindingService bindingService;

    private ApplicationContext applicationContext;

    @Bean
    public MyApplicationJsonMessageMarshallingConverter myApplicationJsonMessageMarshallingConverter(ObjectMapper objectMapper){
        return new MyApplicationJsonMessageMarshallingConverter(objectMapper);
    }

//    @Bean
//    public MyMessageConverter myMessageConverter(){
//        return new MyMessageConverter();
//    }

//    @Bean
//    public MySmartMessageConverter myCompositeMessageConverter(){
//        return new MySmartMessageConverter();
//    }

//    @Bean
//    public GlobalChannelInterceptorWrapper wrapper(){
//        return new GlobalChannelInterceptorWrapper(new MyStreamChannelInterceptor());
//    }

    @Bean
    public MyMessageConverterConfigurer myMessageConverterConfigurer(SubscribableChannelBindingTargetFactory factory) throws NoSuchFieldException, IllegalAccessException {
        Class<? extends SubscribableChannelBindingTargetFactory> aClass = factory.getClass();
        Field messageChannelConfigurer = aClass.getDeclaredField("messageChannelConfigurer");
        messageChannelConfigurer.setAccessible(true);
        Object o = messageChannelConfigurer.get(factory);
        CompositeMessageChannelConfigurer configurer = (CompositeMessageChannelConfigurer) o;
        Class<? extends CompositeMessageChannelConfigurer> aClass1 = configurer.getClass();
        Field messageChannelConfigurers = aClass1.getDeclaredField("messageChannelConfigurers");
        messageChannelConfigurers.setAccessible(true);
        Object o1 = messageChannelConfigurers.get(configurer);
        List<MessageChannelConfigurer> o11 = (List<MessageChannelConfigurer>) o1;
        MyMessageConverterConfigurer myMessageConverterConfigurer = new MyMessageConverterConfigurer();
        o11.add(myMessageConverterConfigurer);

        return myMessageConverterConfigurer;
    }

//    @Bean
//    public MyStreamChannelInterceptor myMessageConverterConfigurer(List<AbstractMessageChannel> channels){
//        MyStreamChannelInterceptor myStreamChannelInterceptor = new MyStreamChannelInterceptor();
//        channels.forEach(e -> {
//            e.addInterceptor(myStreamChannelInterceptor);
//        });
//        return myStreamChannelInterceptor;
//    }

//    @ConditionalOnBean(value = MyMessageConverterConfigurer.class)
//    @Bean
//    public MyStreamChannelInterceptor myStreamChannelInterceptor(List<MessageChannelAndSourceConfigurer> myMessageConverterConfigurer){
//        MyStreamChannelInterceptor interceptor = new MyStreamChannelInterceptor();
//        List<String> destinations = properties.getBindings().values().stream()
//                .filter(e -> e != null && !StringUtils.isEmpty(e.getDestination()))
//                .map(e -> e.getDestination())
//                .collect(Collectors.toList());
//        if(CollectionUtils.isEmpty(destinations)){
//           return interceptor;
//        }
//        for (String destination : destinations) {
//            if (!this.applicationContext.containsBean(destination)) {
//                DirectWithAttributesChannel messageChannel = new DirectWithAttributesChannel();
//                messageChannel.setComponentName(destination);
//                messageChannel.setAttribute("type", Source.OUTPUT);
//                if(!CollectionUtils.isEmpty(myMessageConverterConfigurer)) {
//                    for (MessageChannelAndSourceConfigurer configurer : myMessageConverterConfigurer) {
//                        configurer.configureOutputChannel(messageChannel, destination);
//                    }
//                }
//                ((GenericApplicationContext) applicationContext).registerBean(destination, DirectWithAttributesChannel.class, () -> messageChannel);
//                this.bindingService.bindProducer(messageChannel, destination, false);
//            }
//        }
//        return interceptor;
//    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext= applicationContext;
    }
}
