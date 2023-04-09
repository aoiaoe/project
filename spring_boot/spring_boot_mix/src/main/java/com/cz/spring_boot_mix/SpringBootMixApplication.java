package com.cz.spring_boot_mix;

import com.cz.spring_boot_mix.extention._1MyApplicationContextInitializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

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
    }

}
