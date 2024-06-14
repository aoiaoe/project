package com.cz.test;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jzm
 * @date 2024/5/17 : 14:57
 */
public class TestRegex {

    // 匹配只包含中文、英文以及空格，并且最后一个字符不能为空格
    private static final String REGEX = "^[a-zA-Z\\u4e00-\\u9fa5][\\u4e00-\\u9fa5a-zA-Z ]?[a-zA-Z\\u4e00-\\u9fa5]$";

    public static void main(String[] args) {
        List<String> names = Arrays.asList("张三", " 张三", "张 三", "张三 ",
                "李a四", "李四a2", "李2四", "李四+", "+李四", "李-四",
                "王五 a", "a wang wu", " a wang wu", " a wang wu ",
                "12", "asd", "", " ", "李 ", " 李", "李", " 李 "
        );

        Pattern compile = Pattern.compile(REGEX);
        for (String name : names) {
            Matcher matcher = compile.matcher(name);
            if (matcher.matches()) {
                System.out.println("\"" + name + "\" √" );
            } else {
                System.out.println("\"" + name + "\" ------------->  X" );
            }
        }
    }


}

