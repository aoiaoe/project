package com.cz.spring_boot_mix;

import com.cz.spring_boot_mix.aop.AopServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author alian
 * @date 2020/12/29 下午 3:45
 * @since JDK8
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootMixApplication.class)
public class AopServiceImplTest {

    @Autowired
    private AopServiceImpl aopService;

    @Test
    public void test(){
        this.aopService.doSth();
    }

    @Test
    public void testWithException(){
        try {
            this.aopService.doSthWithException();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testWithOutAroundAspect(){
        this.aopService.doSthWithOutAroundAspectWithOutException();
    }

    @Test
    public void testWithOutAroundAspectWithException(){
        this.aopService.doSthWithOutAroundAspectWithException();
    }

}
