package com.cz.inner_class;

/**
 * 匿名对象和lambda的区别
 *
 * 验证方法: javac AnonymousInnerClassDemo.java
 * 会在目录下观察到编译后的文件， 匿名对象会有 AnonymousInnerClassDemo$1.class类文件
 * 而lambda表达式则没有 AnonymousInnerClassDemo.class 查看反编译结果
 *
 * 还可以是用javap -c
 */
public class AnonymousInnerClassDemo {

    public static void main(String[] args) {

        // 不会生成类对象，而是生成的对应的函数式接口的实例
        Runnable r = () -> System.out.println("a");
        r.run();

        // 匿名对象会生成类文件，然后生成该类的实例
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                System.out.println("a");
            }
        };
        r2.run();
    }

}
