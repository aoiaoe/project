package com.cz.collection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1、树化,前提条件:1、一个桶中元素 > 8个,2、Map中元素总数 > 64
 *          如果桶中元素>8,但是总元素 < 64,这时只会扩容,扩容之后,大于8的桶几率几乎为0
 * 2、退树化,如果树上元素小于<=6,则会退树化为链表
 * @author alian
 * @date 2020/9/17 下午 2:47
 * @since JDK8
 */
public class HashMapDemo {

    public static void main(String[] args) throws Exception {
        HashMap<Integer, Integer> map = new HashMap<>(16);
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(17);
        integers.add(33);
        integers.add(49);
        integers.add(65);
        integers.add(81);
        integers.add(97);
//        integers.add(113);
//        integers.add(129);
//        integers.add(145);
//        integers.add(161);
        final Class<? extends HashMap> aClass = map.getClass();
        Field f = aClass.getDeclaredField("table");
        f.setAccessible(true);
        for (Integer integer : integers) {
            map.put(integer, integer);
            Object[] o = (Object[])f.get(map);
            System.out.println(integer + "\t" + o.length);
//            int h;
//            System.out.println((h = integer.hashCode()) ^ (h >>> 16));
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }

    }

//    integers.add(1);
//        integers.add(65);
//        integers.add(129);
//        integers.add(193);
//        integers.add(257);
//        integers.add(321);
//        integers.add(385);
//        integers.add(449);
//        integers.add(513);
//        integers.add(577);
//        for (Integer integer : integers) {
//        map.put(integer, integer);
//    }

}
