package com.cz.spring_boot_mybatis_dynamic_ds.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "users")
public class Users {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer age;
}
