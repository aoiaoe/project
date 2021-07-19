package com.cz.spring_boot_rabbitmq.stream.config;

import org.springframework.beans.BeansException;
import org.springframework.cloud.stream.binder.PollableMessageSource;
import org.springframework.cloud.stream.binding.MessageChannelAndSourceConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.integration.channel.AbstractMessageChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.util.Assert;

public class MyMessageConverterConfigurer implements MessageChannelAndSourceConfigurer, ApplicationContextAware {

    private ApplicationContext context;

    @Override
    public void configurePolledMessageSource(PollableMessageSource binding, String name) {

    }

    @Override
    public void configureInputChannel(MessageChannel messageChannel, String channelName) {
        this.config(messageChannel);
    }

    @Override
    public void configureOutputChannel(MessageChannel messageChannel, String channelName) {
        this.config(messageChannel);
    }

    public void config(MessageChannel channel){
        Assert.isAssignable(AbstractMessageChannel.class, channel.getClass());
        AbstractMessageChannel messageChannel = (AbstractMessageChannel) channel;
        messageChannel.addInterceptor(new MyStreamChannelInterceptor());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
