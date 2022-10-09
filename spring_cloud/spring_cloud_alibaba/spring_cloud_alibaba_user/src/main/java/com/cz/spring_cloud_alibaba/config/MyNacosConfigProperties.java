package com.cz.spring_cloud_alibaba.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author jzm
 * @date 2022/9/23 : 14:38
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "nacos-config")
public class MyNacosConfigProperties {

    private String test;

    private String testInCommon;

    private String testInExt;
}

