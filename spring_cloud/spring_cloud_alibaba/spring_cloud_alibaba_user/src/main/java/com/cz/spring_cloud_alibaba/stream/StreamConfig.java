package com.cz.spring_cloud_alibaba.stream;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cloud.stream.binder.BinderFactory;
import org.springframework.cloud.stream.config.BindingServiceConfiguration;
import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;

/**
 * @author jzm
 * @date 2022/10/13 : 16:30
 */
//@AutoConfigureAfter(BindingServiceConfiguration.class)
//@Configuration
public class StreamConfig {

    @Bean
    public MyBindingService bindingService(
            BindingServiceProperties bindingServiceProperties,
            BinderFactory binderFactory, TaskScheduler taskScheduler){
        return new MyBindingService(bindingServiceProperties, binderFactory, taskScheduler);
    }
}
