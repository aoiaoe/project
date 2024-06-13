package com.cz.springbootmybatissourcecode.service;

import com.cz.springbootmybatissourcecode.entity.TestDeadlock;
import com.cz.springbootmybatissourcecode.mapper.TestDeadLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
public class TestDeadlockService {

    @Autowired
    private TestDeadLock testDeadLock;

    /**
     * 死锁测试, 先各自都获取到间隙锁，接着都在间隙中插入数据，则会触发死锁，进而 死锁检测，然后innodb会回滚代价最小的事务
     * 业务代码中表现为，一个事务成功，另一个事务抛出死锁异常
     * @param c
     * @param deadlock
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean test(int c, TestDeadlock deadlock){
        testDeadLock.selectForUpdate(c);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        testDeadLock.insertTestDeadLock(deadlock);
        return true;
    }
}
