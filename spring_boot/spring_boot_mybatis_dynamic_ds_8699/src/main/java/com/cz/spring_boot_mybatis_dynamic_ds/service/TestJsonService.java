package com.cz.spring_boot_mybatis_dynamic_ds.service;

import com.cz.spring_boot_mybatis_dynamic_ds.entity.TestJson;
import com.cz.spring_boot_mybatis_dynamic_ds.mapper.TestJsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jzm
 * @date 2024/6/17 : 10:57
 */
@Service
public class TestJsonService {

    @Autowired
    private TestJsonMapper testJsonMapper;

    @Transactional(rollbackFor = Exception.class)
    public boolean insertOut(TestJson testJson, TestJson inner){
        this.testJsonMapper.insert(testJson);
        insertInner(inner);
//        int x = 1 / 0;
        return true;
    }

    public void insertInner(TestJson inner){
        this.testJsonMapper.insert(inner);

    }
}
