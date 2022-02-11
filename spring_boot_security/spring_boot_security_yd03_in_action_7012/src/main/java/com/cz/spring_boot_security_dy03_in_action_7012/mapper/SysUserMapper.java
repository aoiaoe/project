package com.cz.spring_boot_security_dy03_in_action_7012.mapper;

import com.cz.spring_boot_security_dy03_in_action_7012.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author cz
 * @since 2021-07-21
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<String> getAllByUserId(Long userId);
}
