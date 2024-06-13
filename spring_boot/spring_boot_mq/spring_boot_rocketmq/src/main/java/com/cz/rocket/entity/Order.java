package com.cz.rocket.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "orders")
public class Order {

    @TableId(value = "id")
    private Long id;

    private Integer amount;

    private String description;
}
