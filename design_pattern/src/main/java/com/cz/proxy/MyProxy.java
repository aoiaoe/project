package com.cz.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyProxy implements InvocationHandler {

    private Object target;

    public MyProxy(Object object){
        this.target = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("U'r proxied");
        return method.invoke(target, args);
    }

    public static void main(String[] args) {
        Target target = () -> {
            System.out.println("done");
        };
        Target o = (Target)Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new MyProxy(target));
        o.m1();
    }
}

interface Target{
    void m1();
}