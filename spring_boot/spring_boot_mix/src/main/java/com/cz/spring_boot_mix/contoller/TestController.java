package com.cz.spring_boot_mix.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestController {

    /** 线程安全
     * 此对象由AutowireUtils中的ObjectFactoryDelegatingInvocationHandler类进行代理
     */
    @Autowired
    private HttpServletRequest request;

    @GetMapping(value = "/msg")
    public String hello(String msg){
        return request.getParameter("msg");
    }

}
