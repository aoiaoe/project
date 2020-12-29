package com.cz.time;

import java.time.*;

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

        LocalDateTime time = LocalDateTime.of(2020, 12, 23, 11, 57, 0);
        ZonedDateTime utcZoned = ZonedDateTime.of(time, ZoneOffset.UTC);
        ZoneId zoneId = ZoneId.of("Australia/Sydney");
        ZonedDateTime zonedDateTime = utcZoned.withZoneSameInstant(zoneId);
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
        System.out.println(localDateTime);
        final LocalDateTime now = LocalDateTime.now(zoneId);
        System.out.println(now);
    }
}
