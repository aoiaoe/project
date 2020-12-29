package com.cz.spring_boot_test.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static String formatNow(String pattern){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return formatter.format(LocalDateTime.now());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
