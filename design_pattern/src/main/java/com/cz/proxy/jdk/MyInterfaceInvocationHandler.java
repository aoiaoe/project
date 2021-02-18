package com.cz.proxy.jdk;

import com.cz.proxy.MyInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author alian
 * @date 2021/1/15 上午 10:22
 * @since JDK8
 */
public class MyInterfaceInvocationHandler implements InvocationHandler {

    private MyInterface myInterface;

    public MyInterfaceInvocationHandler(MyInterface myInterface){
        this.myInterface = myInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before........");
        Object result = method.invoke(myInterface, args);
        System.out.println("after.......");
        return result;
    }
}
