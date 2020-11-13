package com.cz.collection;

import java.lang.reflect.Field;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 写时复制:读写分离的思想，底层使用对象数组
 *      读取时，读取的是当前对象数组的快照，不加锁
 *      写时，加锁，获取数组快照，修改之后，将数组引用指向修改后的数组
 * @author alian
 * @date 2020/2/25 上午 11:57
 * @since JDK8
 */
public class CopyOnWriteArrayListDemo {

    public static void main(String[] args) throws Exception {

        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

        list.add("1");
        list.add("2");
        list.add("3");


        // clone() 方法是浅拷贝，初始两个list对象底层都指向同一个对象数组,通过反射获取数组比较可得
        CopyOnWriteArrayList<String> list2 = (CopyOnWriteArrayList<String>)list.clone();
        Class<? extends CopyOnWriteArrayList> aClass = list.getClass();
        Field array = aClass.getDeclaredField("array");
        array.setAccessible(true);
        Object[] a = (Object[])array.get(list);
        Object[] b = (Object[])array.get(list2);
        System.out.println(a == b);


        // 当克隆对象作出修改之后，克隆的list修改底层数组，这时底层为不同数组对象
        // 节约空间
        list2.add("4");
        b = (Object[])array.get(list2);
        System.out.println(a == b);

    }
}
