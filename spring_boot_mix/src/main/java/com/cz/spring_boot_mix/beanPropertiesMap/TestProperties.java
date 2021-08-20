package com.cz.spring_boot_mix.beanPropertiesMap;

import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TestProperties{
    private static final DateTimeFormatter FORMATTER_DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final MappedPropertyConfig EXT_CONFIG = new MappedPropertyConfig("#root", "ext");


    public static List<MappedPropertyConfig> TEST = new ArrayList<>();

    static{
        initTest();
    }

    private static void initTest(){
        TEST.add(new MappedPropertyConfig("user_name", "userName"));
        TEST.add(new MappedPropertyConfig("age", "age"));
        TEST.add(new MappedPropertyConfig("birth", "birthday").setValueFunction(toLocalDateTime()));
        TEST.add(EXT_CONFIG);
    }

    public static Function<Object, Object> toInstant() {
        return time -> Optional.ofNullable(time).map(t -> {
            if (StringUtils.isEmpty(t)) {
                return null;
            }
            return LocalDateTime.parse(String.valueOf(time), FORMATTER_DATE_TIME).toInstant(ZoneOffset.of("+8"));
        }).orElse(null);
    }

    public static Function<Object, Object> toLocalDateTime() {
        return time -> Optional.ofNullable(time).map(t -> {
            if (StringUtils.isEmpty(t)) {
                return null;
            }
            return LocalDateTime.parse(String.valueOf(time), FORMATTER_DATE_TIME);
        }).orElse(null);
    }
}
