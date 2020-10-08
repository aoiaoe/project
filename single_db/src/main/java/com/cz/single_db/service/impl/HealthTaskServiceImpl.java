package com.cz.single_db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cz.single_db.mapper.HealthTaskMapper;
import com.cz.single_db.service.IHealthTaskService;
import com.cz.single_db.entity.HealthTask;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cz
 * @since 2020-10-05
 */
@Service
public class HealthTaskServiceImpl extends ServiceImpl<HealthTaskMapper, HealthTask> implements IHealthTaskService {

}
