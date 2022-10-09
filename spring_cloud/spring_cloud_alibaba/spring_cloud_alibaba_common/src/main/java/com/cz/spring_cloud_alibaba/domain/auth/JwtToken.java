package com.cz.spring_cloud_alibaba.domain.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author jzm
 * @date 2022/10/9 : 15:29
 */
@Getter
@Setter
@Builder
public class JwtToken {

    private String token;
}
