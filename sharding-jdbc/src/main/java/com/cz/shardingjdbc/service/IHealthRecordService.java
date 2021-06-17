package com.cz.shardingjdbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cz.shardingjdbc.entity.HealthRecord;

import java.sql.SQLException;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author cz
 * @since 2020-10-05
 */
public interface IHealthRecordService extends IService<HealthRecord> {

    void processHealthRecords();
}
