package com.cz.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * 带时区的时间
 * @author alian
 * @date 2020/11/12 上午 11:01
 * @since JDK8
 */
public class LocalDateTimeDemo {

    public static void main(String[] args) {

        test1();
    }

    // Australia/Sydney有冬令时和夏令时  会自动多算一个小时
    public static void test1(){
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Australia/Sydney"));
        System.out.println(now);

    }
}