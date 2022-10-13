package com.cz.spring_cloud_alibaba.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cz.spring_cloud_alibaba.config.AuthorityConfig;
import com.cz.spring_cloud_alibaba.constants.JwtConstants;
import com.cz.spring_cloud_alibaba.domain.auth.JwtToken;
import com.cz.spring_cloud_alibaba.domain.auth.JwtUserInfo;
import com.cz.spring_cloud_alibaba.domain.auth.LoginDto;
import com.cz.spring_cloud_alibaba.enums.ErrorEnums;
import com.cz.spring_cloud_alibaba.mapper.UsersMapper;
import com.cz.spring_cloud_alibaba.mapper.entity.Users;
import com.cz.spring_cloud_alibaba.service.ILoginService;
import com.cz.spring_cloud_alibaba.utils.AssertUtils;
import com.cz.spring_cloud_alibaba.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;

@Slf4j
@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private AuthorityConfig authorityConfig;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private Key privateKey;

    @Override
    public JwtToken login(LoginDto param) {
        Users user = this.usersMapper.selectOne(new LambdaQueryWrapper<Users>().eq(Users::getName, param.getName()));
        AssertUtils.notNull(user, ErrorEnums.USER_NOT_FOUND);
        boolean match = this.bCryptPasswordEncoder.matches(param.getPwd(), user.getPassword());
        AssertUtils.isTrue(match, ErrorEnums.USER_NOT_FOUND);

        JwtUserInfo info = new JwtUserInfo(user.getId(), user.getName());
        String token = JwtUtils.generateToken(info, 10L, privateKey);

        return JwtToken.builder().token(token).build();
    }
}
