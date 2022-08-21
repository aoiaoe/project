package com.cz.test.dto;

import lombok.Data;

@Data
public class LoginDTO {

    private String userName;

    private String password;

    // 验证码相关
    private String captchaKey;

    private String captchaValue;
}
