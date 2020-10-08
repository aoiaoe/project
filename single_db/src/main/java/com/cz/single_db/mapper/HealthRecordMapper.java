package com.cz.single_db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cz.single_db.entity.HealthRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cz
 * @since 2020-10-05
 */
@Mapper
public interface HealthRecordMapper extends BaseMapper<HealthRecord> {

}
