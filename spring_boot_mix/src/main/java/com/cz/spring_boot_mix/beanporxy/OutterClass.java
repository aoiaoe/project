package com.cz.spring_boot_mix.beanporxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 子类实现接口之后,子类中如果有方法有@Async注解, 在其他地方不能直接注入子类,需要使用接口进行注入
 */
@Service
public class OutterClass {

    // OK
//    @Autowired
//    private Parent child;

    // Child实现父接口Parent
    // 子类中只有普通方法, 可以使用直接注入子类Child
    // 但是如果子类中有方法注解了@Async,因为是实现的接口,Spring会使用JDK动态代理创建代理,所以使用Child注入会报错
    // 两个解决方案: a、使用父接口注入: Parent child;
    //              b、在@EnableSync注解中增加属性@EnableAsync(proxyTargetClass = true), 将代理目标类
//    @Autowired
//    private Child child;

    @Autowired
    private Child child;

    @PostConstruct
    public void post(){
        System.out.println(child.getClass());
//        child.name();
        child.age();
    }
}
