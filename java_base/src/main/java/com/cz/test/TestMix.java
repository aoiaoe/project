package com.cz.test;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jzm
 * @date 2024/6/3 : 14:24
 */
public class TestMix {

    @Test
    public void testStringConstantsPool(){
        String msg = "个人数据-填写错误";
        String msg2 = "个人数据-填写错误";
        String msg3 = new String("个人数据-填写错误");
        System.out.println(msg.length());
        System.out.println(msg == msg2);
        System.out.println(msg == msg3);
        System.out.println(msg2 == msg3);
        msg3 = msg3.intern();
        System.out.println(msg == msg3);
        System.out.println(msg2 == msg3);

    }

    @Test
    public void test1(){
        Map<String, String> map = new HashMap<>();

        String arr[] = new String[]{"aa", "aa", "b", "cb", "cb"};
        for (String s : arr) {
            String res = map.compute("v", (k, old) -> {
                if(s.length() < 2) {
                    return old;
                }
                return s;
            });
            System.out.println("res = " + res);
            System.out.println(map);
            System.out.println("-----");
        }
    }
}
