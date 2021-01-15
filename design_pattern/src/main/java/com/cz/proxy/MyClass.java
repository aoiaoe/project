package com.cz.proxy;

/**
 * @author alian
 * @date 2021/1/15 上午 11:24
 * @since JDK8
 */
public class MyClass implements MyInterface, MyInterface2 {
    @Override
    public void doSth() {
        System.out.println("doSth()");
    }

    @Override
    public void doSth2() {
        System.out.println("doSth2()");
    }
}
