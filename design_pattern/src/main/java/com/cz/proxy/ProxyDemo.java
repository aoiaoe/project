package com.cz.proxy;

/**
 * @author alian
 * @date 2020/11/17 下午 3:02
 * @since JDK8
 */
public class ProxyDemo {

    public static void main(String[] args) {
        testCglibProxy();
    }

    public static void testJdkDynamicProxy() {
        MyInterface instance = new MyInterface() {
            public void doSth() {
                System.out.println("doSth()");
            }
        };
        JdkDynamicProxyHandler jdph = new JdkDynamicProxyHandler(instance);
        MyInterface proxy = (MyInterface)jdph.getProxy();
        proxy.doSth();
    }

    public static void testCglibProxy(){
        SuperClass superClass = new SuperClass();
        CglibDynamicProxy interceptor = new CglibDynamicProxy(superClass);
        SuperClass proxy = (SuperClass)interceptor.getProxy();
        proxy.doSth();
    }
}

