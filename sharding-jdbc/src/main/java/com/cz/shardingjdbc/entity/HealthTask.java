package com.cz.shardingjdbc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "HealthTask对象", description = "")
public class HealthTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "task_id")
    private Long taskId;

    private Long recordId;

    private Long userId;

    private String taskName;


}
