package com.cz.spring_boot_mybatis_dynamic_ds.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cz.spring_boot_mybatis_dynamic_ds.entity.UserBroadcastTable;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author jzm
 * @date 2024/6/17 : 18:25
 */

@Mapper
public interface UserBroadcastTableMapper {

    @DS("shard")
    int insert(UserBroadcastTable entity);
}
