package com.cz.spring_cloud_alibaba_sentinel.config;

import com.cz.spring_cloud_alibaba_sentinel.domain.UserDto;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@ConfigurationProperties(value = "test")
@Configuration
public class UserConfig {

    private List<UserDto> users;
}
