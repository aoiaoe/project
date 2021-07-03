package com.cz.jvm;


/**
 * 静态常量/变量 存放在堆中
 *
 * @author alian
 * @date 2020/11/13 上午 9:57
 * @since JDK8
 */
public class StaticVariableTest {

    /**
     * java.lang.OutOfMemoryError: Java heap space
     * at com.cz.jvm.StaticVariableTest.<clinit>(StaticVariableTest.java:11)
     * Exception in thread "main"
     */
    public static byte[] ARR = new byte[10 * 1024 * 1024];

    public static void main(String[] args) {

        System.out.println("done");
    }
}
