package com.cz.shardingjdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cz.shardingjdbc.entity.User;
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
public interface UsersMapper extends BaseMapper<User> {

    @Select("select user_id, user_name from user order by user_id asc limit 5")
    List<User> selectAll();
}
