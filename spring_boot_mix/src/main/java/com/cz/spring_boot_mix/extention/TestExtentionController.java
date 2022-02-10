package com.cz.spring_boot_mix.extention;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @author jzm
 * @date 2022/1/18 : 16:34
 */
@Slf4j
@RestController
public class TestExtentionController {

    @Autowired
    private TestBean testBean;
    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping(value = "/testBean")
    public TestBean testBean(){
        log.info(testBean + "");
        return testBean;
    }

    @PostConstruct
    public void display(){
        log.info(testBean + "");
    }
}
