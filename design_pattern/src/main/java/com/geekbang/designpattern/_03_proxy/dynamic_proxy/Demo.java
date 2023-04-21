package com.geekbang.designpattern._03_proxy.dynamic_proxy;

import com.geekbang.designpattern._03_proxy.ITargetService;
import com.geekbang.designpattern._03_proxy.TargetServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK动态代理
 * @author jzm
 * @date 2023/4/21 : 11:05
 */
public class Demo {

    public static void main(String[] args) {

        ITargetService service = new TargetServiceImpl();
        ITargetService target = (ITargetService)Proxy.newProxyInstance(ITargetService.class.getClassLoader(), new Class[]{ITargetService.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("动态代理预处理");
                Object res = method.invoke(service, args);
                System.out.println("动态代理后处理");
                return res;
            }
        });

        target.doSth();
    }
}
