package com.cz.shardingjdbc.service.impl;

import com.cz.shardingjdbc.entity.HealthLevel;
import com.cz.shardingjdbc.mapper.HealthLevelMapper;
import com.cz.shardingjdbc.service.IHealthLevelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class HealthLevelServiceImpl extends ServiceImpl<HealthLevelMapper, HealthLevel> implements IHealthLevelService {

}
