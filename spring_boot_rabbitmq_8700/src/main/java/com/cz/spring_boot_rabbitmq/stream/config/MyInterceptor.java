package com.cz.spring_boot_rabbitmq.stream.config;

import com.cz.spring_boot_rabbitmq.delayqueue.config.TenantDataSourceNameHolder;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IdempotentReceiver;
import org.springframework.integration.handler.advice.AbstractHandleMessageAdvice;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Configuration
public class MyInterceptor  {

    @Bean
    @IdempotentReceiver(value = "myInterceptor")
    public AbstractHandleMessageAdvice interceptor(){
        return new AbstractHandleMessageAdvice(){
            @Override
            protected Object doInvoke(MethodInvocation invocation, Message<?> message) throws Throwable {
                if(!message.getHeaders().containsKey("UUID")){
                    invocation.getArguments()[0] =
                            getMessageBuilderFactory()
                                    .fromMessage(message)
                                    .setHeader("UUID", TenantDataSourceNameHolder.get())
                                    .build();
                } else {
                    String uuid = message.getHeaders().get("UUID").toString();
                    TenantDataSourceNameHolder.set(uuid);
                }
                invocation.proceed();
                TenantDataSourceNameHolder.remove();
                return null;
            }
        };
    }
}
