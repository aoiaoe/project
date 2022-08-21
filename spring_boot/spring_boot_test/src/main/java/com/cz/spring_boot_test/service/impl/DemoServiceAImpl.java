package com.cz.spring_boot_test.service.impl;

import com.cz.spring_boot_test.dao.DemoMapper;
import com.cz.spring_boot_test.entity.Demo;
import com.cz.spring_boot_test.service.DemoServiceA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author alian
 * @date 2021/1/21 下午 4:28
 * @since JDK8
 */
@Service
public class DemoServiceAImpl implements DemoServiceA {

    @Autowired
    private DemoMapper demoMapper;

    @Transactional
    @Override
    public void insert() {
        demoMapper.insert(new Demo("张三"));
    }
}
