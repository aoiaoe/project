package com.cz.spring_boot_mix.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author alian
 * @date 2020/12/29 下午 3:21
 * @since JDK8
 */
@Slf4j
@Service("aopServiceImpl")
public class AopServiceImpl {

    /**
     * 有环绕通知,未抛出异常,各切入点的执行顺序:
     *
     * @ @Around(业务逻辑执行前) -> @Before -> 业务逻辑 -> @AfterReturning -> @After -> @Around(业务逻辑执行后)
     * 日志打印:
     * @Around before
     * @Before business logic......
     * @AfterReturning
     * @After
     * @Around after
     */
    @MyAopAnnotation
    public void doSth() {
        log.info("business logic......");
    }

    /**
     * 抛出异常,各切入点的执行顺序:
     *
     * @ @Around(业务逻辑执行前) -> @Before -> 业务逻辑 -> @AfterThrowing -> @After
     * 日志打印
     * @Around before
     * @Before business logic......
     * @AfterThrowing
     * @After
     */
    @MyAopAnnotation
    public void doSthWithException() {
        log.info("business logic......");
        int x = 1 / 0;
    }

    /**
     * 无环绕通知,未抛出异常
     *
     * @ @Before -> 业务逻辑 -> @AfterReturning -> @After
     * 日志打印:
     * @Before business logic......
     * @AfterReturning
     * @After
     */
    @MyAopAnnotation
    public void doSthWithOutAroundAspectWithOutException() {
        log.info("business logic......");
    }

    /**
     * 无环绕通知,未抛出异常
     *
     * @ @Before -> 业务逻辑 ->  @AfterThrowing -> @After
     * 日志打印:
     * @Before business logic......
     * @AfterThrowing
     * @After
     */
    @MyAopAnnotation
    public void doSthWithOutAroundAspectWithException() {
        log.info("business logic......");
        int x = 1 / 0;
    }
}
