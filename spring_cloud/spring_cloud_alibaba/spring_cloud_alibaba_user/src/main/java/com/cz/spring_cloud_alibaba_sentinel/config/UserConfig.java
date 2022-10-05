package com.cz.spring_cloud_alibaba_sentinel.config;

import com.cz.spring_cloud_alibaba_sentinel.domain.UserDto;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * nacos中配置：
 * test:
 *     users:
 *         - {"id": 1,"name": "cz"}
 *         - {"id": 2,"name": "jzm"}
 *         - {"id": 3,"name": "Sephiroth new v5"}
 * 配置热更新: @ConfigurationProperties注解的配置类配合NacosConfig可以实现自动更新
 *          但是如果加上@RefreshScope注解，则是延迟加载，会在使用此类时才会进行更新
 */
//@RefreshScope /*  */
@Slf4j
@Data
@ConfigurationProperties(value = "test")
@Configuration
public class UserConfig{

    private List<UserDto> users;

    @PostConstruct
    public void init(){
        log.info("[设置用户值] :{}", users);
    }

}
