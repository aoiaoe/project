package com.cz.spring_boot_mix.beanPropertiesMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MapperBean {
    private String userName;

    private Integer age;

    private LocalDateTime birthday;

    private String ext;
}
