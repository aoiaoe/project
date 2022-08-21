package com.cz.spring_boot_security_dy03_in_action_7012.dto;

import lombok.Data;

@Data
public class LoginDTO {

    private String userName;

    private String password;

    // 验证码相关
    private String captchaKey;

    private String captchaValue;
}
