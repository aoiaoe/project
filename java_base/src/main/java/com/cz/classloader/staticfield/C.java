package com.cz.classloader.staticfield;


/**
 * 当访问一个Java类或接口中的静态域的时候，
 * 只有真正声明这个域的类或接口才会被初始化
 */
public class C{
    public static void main(String[] args) {
        System.out.println(B.v);
        while (true){
            new B();
        }
    }
}


class A {
    static int v = 100;
    static{
        System.out.println("AAAA");
    }
}

class B extends A{
    static {
        System.out.println("BBB");
    }
}