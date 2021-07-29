package com.cz.spring_boot_mix.contoller;

import com.cz.spring_boot_mix.aop.AopServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/test")
@RestController
public class TestController {

    @GetMapping(value = "/initBinderStringTrimmerEditor")
    public String getStringValue(String value){
        return value;
    }

}
