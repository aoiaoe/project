package com.cz.spring_boot_mix;

import com.cz.spring_boot_mix.beanporxy.Child;
import com.cz.spring_boot_mix.beanporxy.Parent;
import com.cz.spring_boot_mix.beanporxy.ParentClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BeanProxyTest {

    @Autowired
    private Parent child;

    @Autowired
    private Child child2;

    @Test
    public void test1(){
        child.name();
    }


    @Test
    public void test2(){
        child2.age();
    }
}
