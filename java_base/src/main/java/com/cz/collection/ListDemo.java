package com.cz.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
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
    public static void testFor() {
        List list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        for (Object o : list) {
            System.out.println(o);
        }
    }

    @Test
    public void testIterator() {
        List<Data> list = new ArrayList<>();
        for (Long i = 1L; i < 6L; i++) {
            list.add(new Data(i, "name_" + i));
        }
        System.out.println(list);
//        final Iterator<Data> iterator = list.iterator();
//        while (iterator.hasNext()) {
//            Data next = iterator.next();
//            if(next.getId() == 3L){
//                iterator.remove();
//            }
//            if(next.getId() == 4L){
////                next = new Data(7L, "haha");
//                next.setName("xxxxx");
//            }
//        }

//        Data data4 = new Data(4L, "asdasd");
//        for (int i = 0; i < list.size(); i++) {
//            final Data data = list.get(i);
//            if(4 == data.getId()){
//                list.set(i, data4);
//            }
//        }

        for (Data data : list) {
            if (data.getId() == 2) {
                data.setName("aaaaaaa");
            }
        }
        System.out.println(list);
    }

    @Test
    public void test2() {
        List<List<Data>> data = new ArrayList<>();
        List<Data> subData1 = new ArrayList<>();
        subData1.add(new Data(1L, "1"));
        subData1.add(new Data(2L, "2"));
        List<Data> subData2 = new ArrayList<>();
        subData2.add(new Data(3L, "3"));
        subData2.add(new Data(4L, "4"));
        data.add(subData1);
        data.add(subData2);
        final List<Data> collect = data.stream().flatMap(e -> e.stream()).collect(Collectors.toList());
        System.out.println(collect);

    }
}
