package com.cz.spring_cloud_alibaba_gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@SuppressWarnings("all")
@Configuration
@ConfigurationProperties(prefix = "gateway")
public class GatewayConfig {

    private String dataId;

    private String group;

    private Long timeOut;
}
