package com.cz.jvm;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 测试元空间OOM
 *  原因：JDK8及以后，方法区由元空间实现，元空间不存在于堆中，存在于堆外宿主机内存
 *      主要存放类信息
 *
 * 复现需要设置JVM参数: -XX:MetaspaceSize=21m -XX:MaxMetaspaceSize=21m
 * @author jzm
 * @date 2023/4/17 : 13:48
 */
public class MetaspaceTest {

    static class OOM{
    }

    public static void main(String[] args) {
        int i = 0;
        try{
            while (true){
                i++;
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(OOM.class);
                enhancer.setUseCache(false);
                enhancer.setCallback(new MethodInterceptor() {
                    @Override
                    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                        return methodProxy.invokeSuper(o, objects);
                    }
                });
                enhancer.create();
            }
        }catch (Exception e) {
            System.out.println("执行" + i + "次后出现异常");
            e.printStackTrace();
        }
    }


}
