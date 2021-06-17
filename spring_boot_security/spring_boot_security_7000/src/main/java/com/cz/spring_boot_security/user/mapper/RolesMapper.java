package com.cz.spring_boot_security.user.mapper;

import com.cz.spring_boot_security.user.entity.Roles;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Jzm
 * @since 2021-05-28
 */
public interface RolesMapper extends BaseMapper<Roles> {

    List<Roles> selectByUserId(Integer userId);
}
