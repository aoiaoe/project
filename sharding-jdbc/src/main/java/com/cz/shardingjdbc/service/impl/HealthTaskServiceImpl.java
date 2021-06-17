package com.cz.shardingjdbc.service.impl;

import com.cz.shardingjdbc.entity.HealthTask;
import com.cz.shardingjdbc.mapper.HealthTaskMapper;
import com.cz.shardingjdbc.service.IHealthTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author cz
 * @since 2020-10-05
 */
@Service
public class HealthTaskServiceImpl extends ServiceImpl<HealthTaskMapper, HealthTask> implements IHealthTaskService {

}
