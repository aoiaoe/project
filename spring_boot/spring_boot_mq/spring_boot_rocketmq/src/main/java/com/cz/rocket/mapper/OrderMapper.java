package com.cz.rocket.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cz.rocket.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
