package com.cz.spring_cloud_alibaba.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author jzm
 * @date 2022/11/1 : 17:42
 */
@Data
@TableName("Account")
public class Account {

    private Integer id;

    @TableField("user_id")
    private Integer userId;

    private Integer balance;
}
