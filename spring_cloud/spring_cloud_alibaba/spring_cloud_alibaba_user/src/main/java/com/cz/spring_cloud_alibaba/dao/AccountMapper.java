package com.cz.spring_cloud_alibaba.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cz.spring_cloud_alibaba.entity.Account;
import org.apache.ibatis.annotations.Param;

public interface AccountMapper extends BaseMapper<Account> {
    int costFee(@Param("userId") Integer userId, @Param("fee") int fee);
}
