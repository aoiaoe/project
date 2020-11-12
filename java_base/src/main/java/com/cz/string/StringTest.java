package com.cz.string;

import java.util.Arrays;

/**
 * @author alian
 * @date 2020/11/4 上午 9:33
 * @since JDK8
 */
public class StringTest {

    public static void main(String[] args) {

        String str = "param=value";
        String[] param = str.split("param=");
        System.out.println(Arrays.toString(param));
    }
}