package com.cz.spring_cloud_alibaba.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author jzm
 * @date 2022/10/9 : 11:14
 */
@Data
@TableName("users")
public class Users {

    @TableId(value = "id")
    private Integer id;

    private String name;

    private String password;
}
