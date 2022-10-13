package com.cz.spring_cloud_alibaba.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cz.spring_cloud_alibaba.mapper.entity.Users;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersMapper extends BaseMapper<Users> {
}
