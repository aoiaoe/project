package com.cz.springcloudconsumerribbon92xx;

import com.cz.ribbonrule.RibbonRuleCustomizer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@EnableDiscoveryClient
@SpringBootApplication
@RibbonClient(name = "provider", configuration = RibbonRuleCustomizer.class)
public class SpringCloudConsumerRibbon92xxApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConsumerRibbon92xxApplication.class, args);
    }

}
