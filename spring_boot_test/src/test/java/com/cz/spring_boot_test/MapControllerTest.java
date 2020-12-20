package com.cz.spring_boot_test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 启动tomcat环境进行单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootTestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MapControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetValue(){
        String value = restTemplate.getForObject("/map/9", String.class);
        Assert.assertEquals("String_9", value);
    }
}
