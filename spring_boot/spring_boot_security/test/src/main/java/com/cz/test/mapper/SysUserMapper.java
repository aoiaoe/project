package com.cz.test.mapper;

import com.cz.test.entity.SysUser;
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
