package com.cz.securitysdk.constants;

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
