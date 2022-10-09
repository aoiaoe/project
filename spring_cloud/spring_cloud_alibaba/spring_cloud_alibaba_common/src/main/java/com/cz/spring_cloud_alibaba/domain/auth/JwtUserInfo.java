package com.cz.spring_cloud_alibaba.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jzm
 * @date 2022/10/9 : 16:14
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class JwtUserInfo {

    private Integer id;

    private String name;
}
