package com.cz.single_db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cz.single_db.entity.HealthRecord;
import com.cz.single_db.entity.HealthTask;
import com.cz.single_db.mapper.HealthRecordMapper;
import com.cz.single_db.mapper.HealthTaskMapper;
import com.cz.single_db.service.IHealthRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author cz
 * @since 2020-10-05
 */
@Service
public class HealthRecordServiceImpl extends ServiceImpl<HealthRecordMapper, HealthRecord> implements IHealthRecordService {

    @Autowired
    private HealthRecordMapper healthRecordMapper;

    @Autowired
    private HealthTaskMapper healthTaskMapper;

    @Override
    public void processHealthRecords() throws SQLException {
        insertHealthRecords();
    }

    private List<Integer> insertHealthRecords() throws SQLException {
        List<Integer> result = new ArrayList<>(10);
        for (int i = 1; i <= 10; i++) {
            HealthRecord healthRecord = insertHealthRecord(i);
            insertHealthTask(i, healthRecord);
            result.add(healthRecord.getId());
        }
        return result;
    }

    private HealthRecord insertHealthRecord(final int i) throws SQLException {
        HealthRecord healthRecord = new HealthRecord();
        healthRecord.setId(i);
        healthRecord.setLevelId(i % 5);
        healthRecord.setRemark("Remark" + i);
        healthRecordMapper.insert(healthRecord);
        return healthRecord;
    }

    private void insertHealthTask(final int i, final HealthRecord healthRecord) throws SQLException {
        HealthTask healthTask = new HealthTask();
        healthTask.setRecordId(healthRecord.getId());
        healthTask.setUserId(i);
        healthTask.setTaskName("TaskName" + i);
        healthTaskMapper.insert(healthTask);
    }
}
