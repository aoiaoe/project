package com.cz.spring_boot_mix;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author jzm
 * @date 2023/5/24 : 16:59
 */
@Slf4j
public class MultiThreadTransactionTest {

    /**
     * 测试多线程事务.
     */
    @Test
    public void saveThread() {
        ExecutorService service = Executors.newFixedThreadPool(5);
        try {
            //获取线程池
            //拆分数据,拆分5份
            List<Object> lists = new ArrayList(5){{
                add(new Object());
                add(new Object());
                add(new Object());
                add(new Object());
                add(new Object());
            }};
            //执行的线程
            Thread[] threadArray = new Thread[lists.size()];
            //监控子线程执行完毕,再执行主线程,要不然会导致主线程关闭,子线程也会随着关闭
            CountDownLatch countDownLatch = new CountDownLatch(lists.size());
            AtomicBoolean atomicBoolean = new AtomicBoolean(true);
            for (int i = 0; i < lists.size(); i++) {
                if (i == lists.size() - 1) {
                    atomicBoolean.set(false);
                }
                threadArray[i] = new Thread(() -> {
                    //最后一个线程抛出异常
                    boolean flag = atomicBoolean.get();
                    try {

                        if (!flag) {
                            throw new RuntimeException("001出现异常");
                        }
                        log.info("try系统无异常, flag{}", flag);
                    } finally {
                        log.info("finally系统无异常, flag{}", flag);
                        countDownLatch.countDown();
                    }

                });
            }
            for (int i = 0; i < lists.size(); i++) {
                service.execute(threadArray[i]);
            }
            //当子线程执行完毕时,主线程再往下执行
            countDownLatch.await();
            log.info("添加完毕");
        } catch (Exception e) {
            log.info("error", e);
            throw new RuntimeException("002出现异常");
        } finally {
            service.shutdown();
        }
    }
}
