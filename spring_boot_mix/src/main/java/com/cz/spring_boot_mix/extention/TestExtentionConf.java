package com.cz.spring_boot_mix.extention;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jzm
 * @date 2022/1/18 : 16:54
 */
@Configuration
public class TestExtentionConf {

    @Bean
    public TestBean testBean(){
        return new TestBean();
    }
}
