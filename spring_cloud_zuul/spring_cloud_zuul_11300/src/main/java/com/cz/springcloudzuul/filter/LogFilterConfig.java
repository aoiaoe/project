package com.cz.springcloudzuul.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogFilterConfig {

    @Bean
    public LogFilter logFilter(){
        return new LogFilter();
    }

}
