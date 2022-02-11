package com.cz.spring_boot_security_dy03_in_action_7012.context;


import com.cz.securitysdk.entity.LoginUser;

public class LoginUserContextHolder {

    private static ThreadLocal<LoginUser> HOLDER = new ThreadLocal<>();

    public static LoginUser user(){
        return HOLDER.get();
    }

    public static void setContext(LoginUser loginUser){
        HOLDER.set(loginUser);
    }

    public static void clear(){
        HOLDER.remove();
    }
}
