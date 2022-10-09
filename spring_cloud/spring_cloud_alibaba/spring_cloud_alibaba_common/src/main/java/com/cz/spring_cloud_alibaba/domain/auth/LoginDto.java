package com.cz.spring_cloud_alibaba.domain.auth;

import lombok.Data;

/**
 * @author jzm
 * @date 2022/10/9 : 15:28
 */
@Data
public class LoginDto {

    private String name;

    private String pwd;
}
