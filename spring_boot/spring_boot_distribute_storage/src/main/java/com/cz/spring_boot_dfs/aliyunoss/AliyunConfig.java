package com.cz.spring_boot_dfs.aliyunoss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@ConditionalOnProperty(value = "spring.aliyun.enabled", havingValue = "true")
@Data
@Configuration
@PropertySource("classpath:aliyun.properties")
@ConfigurationProperties(prefix = "aliyun")
public class AliyunConfig {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String urlPrefix;


    @Bean
    public OSSClient ossClient(){
        return new OSSClient(endpoint, new DefaultCredentialProvider(accessKeyId, accessKeySecret), null);
    }
}
