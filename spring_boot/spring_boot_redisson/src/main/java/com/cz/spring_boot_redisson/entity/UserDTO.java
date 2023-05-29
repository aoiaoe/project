package com.cz.spring_boot_redisson.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author jzm
 * @date 2023/5/18 : 14:54
 */
@ToString
@Data
public class UserDTO implements Serializable {

    private String name;

    private String sex;

    private Integer age;

    private String token;
}
