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


    /**
     * 外层方法事务传播属性为REQUIRED， 内层方法正常执行
     * 外层方法抛出异常，会导致内层方法全部回滚。
     */
    @Transactional
    @Override
    public void insert_out_exception() {
        this.demoServiceA.insert();
        this.demoServiceB.insert();

        throw new RuntimeException();
    }

    /**
     * 外层方法有事务，调用内层方法，即使是被try-catch,不throw出去,
     * 如果内层方法有事务操作, 依然会导致整个事务回滚，
     * 因为根据事务传播级别，外层内存属于同一个事务，内存回滚会导致外层回滚
     * 证明： 见方法 insert_out_exception_required_new
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insert_out_exception_required() {
        this.demoServiceA.insert();
        try {
//             该方法只抛出异常, 无关数据库回滚操作, 且在外层调用将异常try-catch,故外层事务不会回滚
//            this.demoServiceB.exception();
            // 该方法抛出异常, 且有事务相关操作, 虽然异常被try-catch， 但是依然会回滚
            this.demoServiceB.insert_exception();
        } catch (Exception e) {
            System.out.println("方法回滚");
        }
    }

    /**
     * 此方法为 insert_out_exception_required() 的对照实验
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insert_out_exception_required_new() {
        this.demoServiceA.insert();
        try {
            // 虽然该方法抛出异常, 且有事务相关操作,
            // 但是，内层方法事务传播级别为REQUIRES_NEW
            // 是一个新的事务，且异常被try-catch， 不会回滚
            this.demoServiceB.insert_exception_required_new();
        } catch (Exception e) {
            System.out.println("方法回滚");
        }
    }

}
