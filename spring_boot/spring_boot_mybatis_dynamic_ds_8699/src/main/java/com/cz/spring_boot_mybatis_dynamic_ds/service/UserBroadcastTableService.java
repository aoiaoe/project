package com.cz.spring_boot_mybatis_dynamic_ds.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cz.spring_boot_mybatis_dynamic_ds.entity.UserBroadcastTable;
import com.cz.spring_boot_mybatis_dynamic_ds.mapper.UserBroadcastTableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jzm
 * @date 2024/6/17 : 19:21
 */
@Service
public class UserBroadcastTableService {

    @Autowired
    private UserBroadcastTableMapper mapper;


    public void insert11(UserBroadcastTable entity){
        this.mapper.insert(entity);
    }
}
