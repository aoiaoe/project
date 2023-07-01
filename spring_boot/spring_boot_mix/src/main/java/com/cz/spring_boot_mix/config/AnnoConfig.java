package com.cz.spring_boot_mix.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MyConfigProperties.class)
public class AnnoConfig {
}
