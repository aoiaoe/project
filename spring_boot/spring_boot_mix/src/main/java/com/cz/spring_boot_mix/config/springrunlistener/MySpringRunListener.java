package com.cz.spring_boot_mix.config.springrunlistener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;

/**
 * 接口规范规定: 必须有一个构造器接收 SpringApplication和String[]类型的参数
 */
@Slf4j
public class MySpringRunListener implements SpringApplicationRunListener {

    private final SpringApplication application;

    private final String[] args;

    public MySpringRunListener(SpringApplication springApplication, String[] args){
        this.application = springApplication;
        this.args = args;
    }

    @Override
    public void starting(ConfigurableBootstrapContext bootstrapContext) {
        System.out.println("----------------------> Spring Run Listener Starting Spring启动监听器!!!!!!!");
        log.info("----------------------> Spring Run Listener Starting Spring启动监听器!!!!!!!");
    }
}
