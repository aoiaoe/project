package com.cz.shardingjdbc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author cz
 * @since 2020-10-05
 */
@Data
public class HealthRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "record_id")
    private Long recordId;

    private Long userId;

    private Long levelId;

    private String remark;

}
