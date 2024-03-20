package com.cz.spring_boot_mix.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * SpringApplicationRunner和CommandLineRunner中的死循环对于程序处理http请求无太大影响
 * 因为此处调用是在Spring容器启动，Web容器启动之后，才会进行调用
 * 已经不影响Web容器进行网络请求的服务
 * 但是可能会影响一些Spring容器带来的额外功能，因为此处死循环，会阻塞一些监听器的执行
 * @author jzm
 * @date 2023/3/29 : 17:18
 */
@Slf4j
//@Component
public class MyCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("开始CommandLineRunner中的死循环");
        while (true){

        }
    }
}
