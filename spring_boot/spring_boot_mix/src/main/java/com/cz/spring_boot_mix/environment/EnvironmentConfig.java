package com.cz.spring_boot_mix.environment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.AbstractEnvironment;

import javax.annotation.PostConstruct;

@Configuration
public class EnvironmentConfig {

    public static final String DYNAMIC_CONFIG_NAME = "dynamic_config";

    @Autowired
    private AbstractEnvironment environment;

    @PostConstruct
    public void post(){
        environment.getPropertySources().addFirst(new DynamicPropertySource(DYNAMIC_CONFIG_NAME));
    }
}
