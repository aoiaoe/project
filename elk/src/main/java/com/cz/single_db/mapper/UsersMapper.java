package com.cz.single_db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cz.single_db.entity.Users;
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

    @Select("select * from users")
    List<Users> selectAll();
}
