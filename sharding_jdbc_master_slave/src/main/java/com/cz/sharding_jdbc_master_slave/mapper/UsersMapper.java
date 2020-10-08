package com.cz.sharding_jdbc_master_slave.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cz.sharding_jdbc_master_slave.entity.Users;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cz
 * @since 2020-10-05
 */
public interface UsersMapper extends BaseMapper<Users> {

    @Select("select id, name from user order by id asc limit 5")
    List<Users> selectAll();
}
