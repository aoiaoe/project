package com.cz.spring_boot_mix.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
public class MyConfig {


    /**
     * 系统环境变量中存在一个键叫做 config_test， 注意测试的时候如果是新设置值，需要重启idea加载系统变量
     * 使用场景: 云原生环境中，例如k8s中，可以将一些重要参数通过环境变量的方式设置，应用直接读取环境变量进行初始化
     */
    @Value("${config_test_in_yml}")
    private String configTest;
    @Value("${config_test}")
    private String configTest1;

    @PostConstruct
    public void init(){
        log.info("[测试读取系统环境变量] :{}", configTest);
    }
    /**
     * 只需要容器中存在bean就会调用
     * @return
     */
    @Bean
    public MyExceptionResolver myExceptionResolver(){
        return new MyExceptionResolver();
    }
}
