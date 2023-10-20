package com.cz.rpc.proxy;


import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.Proxy;

/**
 * @Author linhao
 * @Date created in 8:56 上午 2021/11/30
 */
public class JDKProxyFactory{

    public <T> T getProxy(final Class clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz},
                new JDKClientInvocationHandler(clazz));
    }

}
