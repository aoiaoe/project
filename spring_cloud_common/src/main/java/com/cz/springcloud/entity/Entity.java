package com.cz.springcloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
