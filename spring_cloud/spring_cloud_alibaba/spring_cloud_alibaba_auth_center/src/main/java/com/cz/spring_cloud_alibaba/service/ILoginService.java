package com.cz.spring_cloud_alibaba.service;

import com.cz.spring_cloud_alibaba.domain.auth.JwtToken;
import com.cz.spring_cloud_alibaba.domain.auth.LoginDto;

/**
 * @author jzm
 * @date 2022/10/9 : 15:23
 */
public interface ILoginService {

    JwtToken login(LoginDto param);
}
