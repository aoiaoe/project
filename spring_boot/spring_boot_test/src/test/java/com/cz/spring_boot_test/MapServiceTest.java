package com.cz.spring_boot_test;

import com.cz.spring_boot_test.service.MapService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 如果测试的时候需要用到springboot的组件，则需要启用springbootTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootTestApplication.class)
public class MapServiceTest {

    @Autowired
    private MapService mapService;

    @Test
    public void testGetValue() {
        String value = mapService.getValue("9");
        Assert.assertEquals("String_9", value);

    }
}
