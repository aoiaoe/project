package com.cz.spring_boot_mix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    /**
     * 只需要容器中存在bean就会调用
     * @return
     */
    @Bean
    public MyExceptionResolver myExceptionResolver(){
        return new MyExceptionResolver();
    }
}
