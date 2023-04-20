package com.cz.classloader.demo1;

/**
 * @author jzm
 * @date 2023/4/18 : 10:59
 */
public class ClassLoadTest {

    public static void main(String[] args) {

        System.out.println("main before load class");
        Parent p = new Parent();
    }
}
