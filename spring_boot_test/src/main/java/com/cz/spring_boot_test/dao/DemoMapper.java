package com.cz.spring_boot_test.dao;

import com.cz.spring_boot_test.entity.Demo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author alian
 * @since 2021-01-21
 */
@Repository
public interface DemoMapper extends BaseMapper<Demo> {

}
