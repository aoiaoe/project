package com.cz.test.mapper;

import com.cz.test.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author cz
 * @since 2021-07-21
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> getRolesByUserId(Long userId);
}
