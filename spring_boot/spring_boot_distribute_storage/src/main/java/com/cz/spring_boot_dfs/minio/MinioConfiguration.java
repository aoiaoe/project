package com.cz.spring_boot_dfs.minio;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "spring.minio")
@Data
public class MinioConfiguration {

    private boolean enabled = true;

    private String key;
  
    private String secret;

    private String url;  
  
    private String bucketName;

    private List<String> bucketNames;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()  
                .endpoint(url)  
                .credentials(key, secret)
                .build();
    }
}