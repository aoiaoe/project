package com.cz.collection;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alian
 * @date 2020/11/16 下午 6:02
 * @since JDK8
 */
public class ListDemo {

    public static void main(String[] args) {
       testFor();
    }

    /**
     * list的for语法，是一个语法糖,本质是调用list的iterator()方法获取其迭代器
     * 判断是否有元素使用迭代器的hasNext()方法
     * 如果要在循环时对list进行删除操作,不能使用List接口的删除方法,需使用迭代器的remove()方法，
     * 否则会抛并发修改异常，进行快速失败
     */
    public static void testFor(){
        List list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        for (Object o : list) {
            System.out.println(o);
        }
    }
}
