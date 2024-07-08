package com.cz.collection;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author alian
 * @date 2021/2/8 下午 5:26
 * @since JDK8
 */
public class HashMapCglibProxy<T> implements InvocationHandler {

    private T target;

    public HashMapCglibProxy(T target) {
        this.target = target;
    }

//    @Override
//    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
//        if(method != null && "resize".equals(method.getName())){
//            System.out.println("called resize");
//        }
//        System.out.println("before...");
//        final Object invoke = method.invoke(target, objects);
//        System.out.println("agter...");
//        return invoke;
//    }

    public T getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return (T) enhancer.create();
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        if (method != null && "resize".equals(method.getName())) {
            System.out.println("called resize");
        }
//        System.out.println("before...");
        final Object invoke = method.invoke(target, objects);
//        System.out.println("after...");
        return invoke;
    }
}
