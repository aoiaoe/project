package com.cz.springcloud.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author alian
 * @date 2020/10/9 下午 3:34
 * @since JDK8
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Entity {

    private Integer id;

    private String message;

    private String extra;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime time;
}
