package com.cz.spring_boot_test.service.impl;

import com.cz.spring_boot_test.service.DemoService;
import com.cz.spring_boot_test.service.DemoServiceA;
import com.cz.spring_boot_test.service.DemoServiceB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author alian
 * @date 2021/1/21 下午 5:19
 * @since JDK8
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoServiceA demoServiceA;
    @Autowired
    private DemoServiceB demoServiceB;

    @Transactional
    @Override
    public void insert_out_exception() {
        this.demoServiceA.insert();
        this.demoServiceB.insert();

        throw new RuntimeException();
    }

    /**
     * 外层方法有事务，调用内层方法，即使是被try-catch,不throw出去,
     * 如果内层方法有事务操作, 依然会导致整个事务回滚
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insert_out_exception_required() {
        this.demoServiceA.insert();
        try {
            // 该方法只抛出异常, 无关数据库回滚操作, 且在外层调用将异常try-catch,故外层事务不会回滚
//            this.demoServiceB.exception();
            // 改方法抛出异常, 且有事务相关操作, 虽然异常被try-catch， 但是依然会回滚
            this.demoServiceB.insert_exception();
        } catch (Exception e) {
            System.out.println("方法回滚");
        }
    }

}
