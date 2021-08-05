package com.cz.proxy;

import com.cz.proxy.cglib.CglibDynamicProxy;
import com.cz.proxy.jdk.JdkDynamicProxyHandler;
import com.cz.proxy.jdk.MyInterfaceInvocationHandler;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

/**
 * @author alian
 * @date 2020/11/17 下午 3:02
 * @since JDK8
 */
public class ProxyDemo {


    @Test
    public void test1(){
        final ClassLoader classLoader = ProxyDemo.class.getClassLoader();
        Class[] clazz = new Class[]{MyInterface3.class};
        MyInterface3 myInterface3 = (MyInterface3) Proxy.newProxyInstance(classLoader, clazz, (proxy, mehtod, args) -> {
            return "U r proxied " + mehtod.getName();
        });

        System.out.println(myInterface3.doSth());
        System.out.println(myInterface3.doSth2());

    }

    @Test
    public void testProxy() {
        MyClass instance = new MyClass();
        MyInterfaceInvocationHandler handler = new MyInterfaceInvocationHandler(instance);
        Object proxy = Proxy.newProxyInstance(instance.getClass().getClassLoader(),
                instance.getClass().getInterfaces(), handler);
        ((MyInterface) proxy).doSth();
        ((MyInterface2) proxy).doSth2();
    }

    @Test
    public void testJdkDynamicProxy() {
        MyInterface instance = new MyInterface() {
            public void doSth() {
                System.out.println("doSth()");
            }
        };
        JdkDynamicProxyHandler jdph = new JdkDynamicProxyHandler(instance);
        MyInterface proxy = (MyInterface) jdph.getProxy();
        proxy.doSth();
    }

    @Test
    public void testCglibProxy() {
        SuperClass superClass = new SuperClass();
        CglibDynamicProxy interceptor = new CglibDynamicProxy(superClass);
        SuperClass proxy = (SuperClass) interceptor.getProxy();
        proxy.doSth();
    }
}

