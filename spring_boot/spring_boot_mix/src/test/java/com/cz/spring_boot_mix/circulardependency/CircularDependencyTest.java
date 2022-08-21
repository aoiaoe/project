package com.cz.spring_boot_mix.circulardependency;

import com.cz.spring_boot_mix.circulardependency.autowired.Service1;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author jzm
 * @date 2022/8/18 : 14:23
 */
public class CircularDependencyTest {

    @Test
    public void test1(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        Service1 service1 = applicationContext.getBean(Service1.class);
        service1.testDosth();
    }
}
