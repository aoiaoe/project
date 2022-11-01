package com.cz.spring_cloud_alibaba.service;

import com.cz.spring_cloud_alibaba.dao.AccountMapper;
import com.cz.spring_cloud_alibaba.feign.OrderFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AccountService {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private OrderFeignClient orderFeignClient;

    public boolean createOrder(Integer userId, int fee){
        int res = this.accountMapper.costFee(userId, fee);
        this.orderFeignClient.createOrder(userId, fee);
        if(res != 1){
            throw new IllegalArgumentException("余额不足");
        }
        return res == 1;
    }
}