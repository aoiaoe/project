package com.cz;

import com.cz.spring_boot_test.SpringBootTestApplication;
import com.cz.spring_boot_test.service.DemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author alian
 * @date 2021/1/21 下午 5:07
 * @since JDK8
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootTestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionTest {
    @Autowired
    private DemoService demoService;

    @Test
    public void insert_outer_exception(){
        try {
            this.demoService.insert_out_exception();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void insert_outer_exception_required(){
        try {
            this.demoService.insert_out_exception_required();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
