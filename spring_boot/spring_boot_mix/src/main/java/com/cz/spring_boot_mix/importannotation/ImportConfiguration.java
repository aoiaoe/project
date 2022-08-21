package com.cz.spring_boot_mix.importannotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author jzm
 * @date 2022/1/13 : 10:09
 */
@Configuration
@Import({TestA.class,TestB.class,
        TestCImportSelector.class,TestDImportBeanDefinitionRegistrar.class})
public class ImportConfiguration {

    @Autowired
    private TestA testA;
    @Autowired
    private TestB testB;
    @Autowired
    private TestC testC;
    @Autowired
    private TestD testD;
    @Bean
    public ApplicationRunner applicationRunner(){
        return e -> {
            testA.testA();
            testB.testB();
            testC.testC();
            testD.testD();
        };
    }
}
