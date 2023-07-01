package com.cz.spring_boot_mix.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "config")
public class MyConfigProperties {

    private String msg;
}
