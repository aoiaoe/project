package com.cz.spring_boot_security_dy03_in_action_7012.enums;

public enum UserStatus {

    NORMAL("0"),
    DISABLE("1"),
    DELETED("2"),
    ;

    private String code;
    private UserStatus(String code){
        this.code = code;
    }
    public String getCode(){
        return code;
    }
}
