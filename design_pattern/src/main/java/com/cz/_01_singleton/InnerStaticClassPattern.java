package com.cz._01_singleton;

/**
 * 外部类没有静态属性，因此不会像饿汉式立即加载对象。
 *
 * 只有当调用公共方法（getInstance）时，才会加载静态内部类。加载内部类的过程是线程安全的。
 *
 * 内部类中通过 static final 确保内存中只有一个外部类的实例，因为实例变量（tm）只能被赋值一次。
 */
public class InnerStaticClassPattern {

    private InnerStaticClassPattern(){
        System.out.println("InnerStaticClassPattern constructor");
    }

    private static class Holder{
        public static InnerStaticClassPattern instance = new InnerStaticClassPattern();
    }

    public static InnerStaticClassPattern getInstance(){
        return Holder.instance;
    }

    public static void main(String[] args) {
        System.out.println("main");
        InnerStaticClassPattern.getInstance();
    }

}
