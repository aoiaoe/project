package com.cz.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK动态代理, 只能基于接口进行代理
 * @author alian
 * @date 2020/11/17 下午 3:20
 * @since JDK8
 */
public class JdkDynamicProxyHandler implements InvocationHandler {

    private Object target;

    public JdkDynamicProxyHandler(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before doSth()");
        method.invoke(target, args);
        System.out.println("after doSth()");
        return null;
    }

    public Object getProxy(){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),
                target.getClass().getInterfaces(), this);
    }
}
