package com.cz.spring_boot_mybatis_dynamic_ds.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author jzm
 * @date 2024/6/17 : 18:24
 */
@Data
@TableName(value = "user_broadcast_table")
public class UserBroadcastTable {

    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;

    @TableField(value = "name")
    private String name;
}
