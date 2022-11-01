package com.cz.spring_cloud_alibaba.controller;

import com.cz.spring_cloud_alibaba.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jzm
 * @date 2022/11/1 : 17:37
 */
@RequestMapping(value = "/seata")
@RestController
public class SeataController {

    @Autowired
    private AccountService accountService;

    @PostMapping(value = "/test")
    public boolean test(Integer id, Integer fee){
        return this.accountService.createOrder(id, fee);
    }
}
