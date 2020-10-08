package com.cz.single_db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cz.single_db.entity.HealthLevel;
import com.cz.single_db.mapper.HealthLevelMapper;
import com.cz.single_db.service.IHealthLevelService;
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
