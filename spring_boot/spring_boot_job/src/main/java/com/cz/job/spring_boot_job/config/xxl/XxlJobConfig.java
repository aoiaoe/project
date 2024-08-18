package com.cz.job.spring_boot_job.config.xxl;

import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(XxlJobProperties.class)
public class XxlJobConfig {

    public static final String XXL_PROP_PREFIX = "xxl";


    @Bean
    public XxlJobExecutor xxlJobExecutor(XxlJobProperties xxlJobProperties) throws Exception {
        XxlJobExecutor executor = new XxlJobSpringExecutor();
        executor.setAdminAddresses(xxlJobProperties.getAdminAddress());
        executor.setAccessToken(xxlJobProperties.getToken());
        executor.setAppname(xxlJobProperties.getAppName());
        executor.setLogPath(xxlJobProperties.getLogPath());
        executor.setAddress(xxlJobProperties.getAddress());
        executor.setIp(xxlJobProperties.getIp());
        executor.setPort(xxlJobProperties.getPort());
        executor.start();
        return executor;
    }
}
