package com.cz.spring_boot_security_dy03_in_action_7012.service.impl;

import com.cz.spring_boot_security_dy03_in_action_7012.entity.SysRoleMenu;
import com.cz.spring_boot_security_dy03_in_action_7012.mapper.SysRoleMenuMapper;
import com.cz.spring_boot_security_dy03_in_action_7012.service.ISysRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色和菜单关联表 服务实现类
 * </p>
 *
 * @author cz
 * @since 2021-07-21
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

}
