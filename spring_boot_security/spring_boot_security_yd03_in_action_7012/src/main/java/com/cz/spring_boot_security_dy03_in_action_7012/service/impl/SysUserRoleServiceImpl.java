package com.cz.spring_boot_security_dy03_in_action_7012.service.impl;

import com.cz.spring_boot_security_dy03_in_action_7012.entity.SysUserRole;
import com.cz.spring_boot_security_dy03_in_action_7012.mapper.SysUserRoleMapper;
import com.cz.spring_boot_security_dy03_in_action_7012.service.ISysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户和角色关联表 服务实现类
 * </p>
 *
 * @author cz
 * @since 2021-07-21
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

}
