package com.cz.shardingjdbc.service.impl;

import com.cz.shardingjdbc.entity.HealthLevel;
import com.cz.shardingjdbc.entity.HealthRecord;
import com.cz.shardingjdbc.entity.HealthTask;
import com.cz.shardingjdbc.mapper.HealthLevelMapper;
import com.cz.shardingjdbc.mapper.HealthRecordMapper;
import com.cz.shardingjdbc.mapper.HealthTaskMapper;
import com.cz.shardingjdbc.service.IHealthRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    @Autowired
    private HealthLevelMapper healthLevelMapper;

    @Override
    public void processHealthRecords(){
        insertHealthRecords();
    }

    private List<Long> insertHealthRecords(){
        List<Long> result = new ArrayList<>(10);
        for (Long i = 1L; i <= 10; i++) {
            HealthRecord healthRecord = insertHealthRecord(i);
            insertHealthTask(i, healthRecord);
            result.add(healthRecord.getRecordId());
        }
        // save level
        this.saveLevel();
        return result;
    }

    private HealthRecord insertHealthRecord(final Long i){
        HealthRecord healthRecord = new HealthRecord();
//        healthRecord.setRecordId(i);
        healthRecord.setUserId(i);
        healthRecord.setLevelId(i % 5);
        healthRecord.setRemark("Remark" + i);
        healthRecordMapper.insert(healthRecord);
        return healthRecord;
    }

    private void insertHealthTask(final Long i, final HealthRecord healthRecord){
        HealthTask healthTask = new HealthTask();
        healthTask.setRecordId(healthRecord.getRecordId());
        healthTask.setUserId(i);
        healthTask.setTaskName("TaskName" + i);
        healthTaskMapper.insert(healthTask);
    }

    private void saveLevel(){
        for (Long i = 0L; i < 5; i++) {
            HealthLevel level = new HealthLevel();
            level.setLevelId(i);
            level.setLevelName("levet_" + i);
            this.healthLevelMapper.insert(level);
        }
    }
}
