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

    @PostMapping(value = "/testMainError")
    public boolean testMainError(Integer id, Integer fee, Boolean error, Boolean timeOut){
        return this.accountService.createOrder(id, fee, error, timeOut);
    }
}
