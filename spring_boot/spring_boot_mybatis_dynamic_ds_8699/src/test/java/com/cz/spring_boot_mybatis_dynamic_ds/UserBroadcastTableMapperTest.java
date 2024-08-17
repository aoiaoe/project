package com.cz.spring_boot_mybatis_dynamic_ds;

import com.cz.spring_boot_mybatis_dynamic_ds.entity.UserBroadcastTable;
import com.cz.spring_boot_mybatis_dynamic_ds.mapper.UserBroadcastTableMapper;
import com.cz.spring_boot_mybatis_dynamic_ds.service.UserBroadcastTableService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author jzm
 * @date 2024/6/17 : 18:33
 */
@SpringBootTest
public class UserBroadcastTableMapperTest {

    @Autowired
    private UserBroadcastTableService service;

    @Test
    public void save(){
        UserBroadcastTable entity = new UserBroadcastTable();
        entity.setId(1001);
        entity.setName("这是一个广播表，两个数据库都应该存在这条记录");
        this.service.insert11(entity);
    }
}
