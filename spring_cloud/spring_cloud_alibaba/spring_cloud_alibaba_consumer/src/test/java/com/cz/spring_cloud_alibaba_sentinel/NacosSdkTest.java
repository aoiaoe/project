package com.cz.spring_cloud_alibaba_sentinel;


import static com.alibaba.nacos.api.PropertyKeyConst.*;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.junit.jupiter.api.Test;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

public class NacosSdkTest {

    @Test
    public void nocosSdkTest() throws Exception {
        final Properties properties = new Properties();
        properties.put(SERVER_ADDR, "http://tx-gd:8948");
        properties.put(NAMESPACE, "dev");
        properties.put(USERNAME, "nacos");
        properties.put(PASSWORD, "nacos");
        final ConfigService configService = NacosFactory.createConfigService(properties);
        String dataId = "nacos_config_sentinel_consumer.yml";
        String group = "DEFAULT_GROUP";
        final String default_group = configService.getConfig(dataId, group, 3000);
        System.out.println(default_group);
        configService.addListener(dataId, group, new Listener() {
            @Override
            public Executor getExecutor() {
                return null;
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                System.out.println("-------配置发生变更，变更后的配置：" + configInfo);
            }
        });

        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();
    }
}
