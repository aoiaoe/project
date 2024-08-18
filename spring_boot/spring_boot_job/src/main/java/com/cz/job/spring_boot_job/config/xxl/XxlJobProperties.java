package com.cz.job.spring_boot_job.config.xxl;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(value = XxlJobConfig.XXL_PROP_PREFIX)
public class XxlJobProperties {

    private String adminAddress;

    private String address;

    private String ip;

    private Integer port;

    private String token;

    private String appName;

    private String logPath;

}
