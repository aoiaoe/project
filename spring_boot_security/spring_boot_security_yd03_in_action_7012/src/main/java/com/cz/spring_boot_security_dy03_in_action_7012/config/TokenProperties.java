package com.cz.spring_boot_security_dy03_in_action_7012.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Data
@Configuration
@ConfigurationProperties(prefix = "token")
public class TokenProperties {

    private Integer expireSeconds;

    private String secretKey;

    private String headerName;

    private Set<String> ignoreUrls;

}