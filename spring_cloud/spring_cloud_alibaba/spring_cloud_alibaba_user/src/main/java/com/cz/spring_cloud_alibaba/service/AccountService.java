package com.cz.spring_cloud_alibaba.service;

import com.cz.spring_cloud_alibaba.dao.AccountMapper;
import com.cz.spring_cloud_alibaba.feign.OrderFeignClient;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AccountService {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private OrderFeignClient orderFeignClient;

    @GlobalTransactional(name = "test", rollbackFor = Exception.class)
    public boolean createOrder(Integer userId, int fee, Boolean error, Boolean timeOut){
        int res = this.accountMapper.costFee(userId, fee);
        this.orderFeignClient.createOrder(userId, fee, timeOut);
        if(error){
            throw new RuntimeException("出错啦");
        }
        return res == 1;
    }
}