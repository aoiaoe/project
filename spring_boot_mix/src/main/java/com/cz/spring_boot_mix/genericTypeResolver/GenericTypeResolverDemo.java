package com.cz.spring_boot_mix.genericTypeResolver;

import org.springframework.core.GenericTypeResolver;
import org.junit.Test;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.Arrays;

public class GenericTypeResolverDemo {

    public static void main(String[] args) throws NoSuchMethodException {
    }

    /**
     * 获取某方法的返回值类型
     * @throws NoSuchMethodException
     */
    @Test
    public void test1() throws NoSuchMethodException {
        GenericAbstracClass demo = new GenericConcretClass();
        final Method doSth = demo.getClass().getDeclaredMethod("doSth", String.class);
        System.out.println(GenericTypeResolver.resolveReturnType(doSth, String.class));
    }

    /**
     * 解析某抽象类或者接口的子类所带的泛型类型
     * 如果没有泛型则返回null
     */
    @Test
    public void test2(){
        System.out.println(GenericTypeResolver.resolveTypeArgument(GenericConcretClass.class, GenericAbstracClass.class));
        System.out.println(GenericTypeResolver.resolveTypeArgument(GenericTypeResolverDemo.class, Object.class));
        System.out.println(Arrays.toString(GenericTypeResolver.resolveTypeArguments(GenericConcretClass.class, GenericAbstracClass.class)));
        System.out.println(GenericTypeResolver.resolveTypeArguments(GenericTypeResolverDemo.class, Object.class));
        for (StackTraceElement element : new RuntimeException().getStackTrace()) {
            System.out.println(element);
        }
    }

    /**
     * 检测是否是同一个类或者是其超类
     */
    @Test
    public void test3(){
        System.out.println(GenericAbstracClass.class.isAssignableFrom(GenericConcretClass.class));
        System.out.println(GenericConcretClass.class.isAssignableFrom(GenericAbstracClass.class));
        System.out.println(GenericAbstracClass.class.isAssignableFrom(GenericAbstracClass.class));
        System.out.println(GenericConcretClass.class.isAssignableFrom(GenericConcretClass.class));
    }
}
