package com.cz.spring_boot_mix;

import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.cz.spring_boot_mix.config.MyConfigProperties;
import com.cz.spring_boot_mix.extention._1MyApplicationContextInitializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.concurrent.ScheduledExecutorService;

@EnableFeignClients(basePackages = "com.cz.spring_boot_mix.contoller.feign")
@Slf4j
@EnableAspectJAutoProxy
@EnableAsync
//@EnableScheduling
@SpringBootApplication
public class SpringBootMixApplication {

    public static void main(String[] args) {
        SpringApplication build = new SpringApplicationBuilder()
                // 配置容器初始化器
                .initializers(new _1MyApplicationContextInitializer())
                .sources(SpringBootMixApplication.class)
                .build();
        build.run(args);
        log.info("项目启动完成");
        try {
            Class<FlowRuleManager> aClass = FlowRuleManager.class;
            Field scheduler = aClass.getDeclaredField("SCHEDULER");
            scheduler.setAccessible(true);
            ScheduledExecutorService o = (ScheduledExecutorService)scheduler.get(aClass.newInstance());
            o.shutdown();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Autowired
    private MyConfigProperties config;
    @PostConstruct
    public void test(){
        log.info("[测试使用@EnableConfigurationProperties能否注入属性类的bean]:{}", config);
    }

}
