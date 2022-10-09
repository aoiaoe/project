package com.cz.spring_cloud_alibaba.service;

import com.cz.spring_cloud_alibaba.SpringCloudAlibabaAuthCenterApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author jzm
 * @date 2022/10/9 : 15:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringCloudAlibabaAuthCenterApplication.class)
public class PasswordEncoderTest {

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Test
    public void pwEncodeTest(){
        System.out.println(bCryptPasswordEncoder.encode("123456"));
    }
}
