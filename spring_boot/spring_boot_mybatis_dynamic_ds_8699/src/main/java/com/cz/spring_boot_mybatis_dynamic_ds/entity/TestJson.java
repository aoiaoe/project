package com.cz.spring_boot_mybatis_dynamic_ds.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.JdbcType;

/**
 * @author jzm
 * @date 2024/5/23 : 16:43
 */
@Data
@TableName(value = "test_json", autoResultMap = true)
@AllArgsConstructor
@NoArgsConstructor
public class TestJson {

    @TableId(type = IdType.ASSIGN_ID)
    private int id;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Book jsonString;

    @TableField(jdbcType = JdbcType.JAVA_OBJECT, typeHandler = JacksonTypeHandler.class)
    private Book jsonObj;

}
