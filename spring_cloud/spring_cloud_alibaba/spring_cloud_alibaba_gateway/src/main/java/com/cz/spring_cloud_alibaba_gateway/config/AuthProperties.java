package com.cz.spring_cloud_alibaba_gateway.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {

    private List<String> permitsUrl;


    @PostConstruct
    public void init(){
        log.info("[认证配置类] {}", permitsUrl);
    }
}