package com.cz.null1;

/**
 * 两个知识点:
 * 1、null可以强转成任何类型， (Object)null ==> Object obj = null;
 * 2、method()是静态方法, 不需要对象也可以调用, 打开字节码文件会发现, null.staticMethod()被重写成 Class.staticMethod();
 *
 * @author alian
 * @date 2021/2/25 下午 3:46
 * @since JDK8
 */
public class NullDemo {

    public static void method() {
        System.out.println("Hello");
    }

    public static void main(String[] args) {
        ((NullDemo) null).method();

        // 上面的写法 等同于 下面的写法

//        NullDemo nullDemo = null;
//        nullDemo.method();
    }
}
