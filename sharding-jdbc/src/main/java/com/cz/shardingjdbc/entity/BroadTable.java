package com.cz.shardingjdbc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 测试sharding jdbc广播表
 * @author jzm
 * @date 2024/7/8 : 16:36
 */
@Data
@TableName("broad_table_test")
public class BroadTable {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String name;
}
