package com.cz.shardingjdbc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author cz
 * @since 2020-10-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "HealthRecord对象")
public class HealthLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "level_id")
    private Long levelId;

    private String levelName;


}
