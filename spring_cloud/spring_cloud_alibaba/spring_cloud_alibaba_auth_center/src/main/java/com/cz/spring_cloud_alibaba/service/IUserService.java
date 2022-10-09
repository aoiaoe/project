package com.cz.spring_cloud_alibaba.service;

import com.cz.spring_cloud_alibaba.domain.user.UserVo;

public interface IUserService {
    UserVo user(Integer id);
}
