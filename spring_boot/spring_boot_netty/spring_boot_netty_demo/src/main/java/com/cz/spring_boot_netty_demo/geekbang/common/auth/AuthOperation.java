package com.cz.spring_boot_netty_demo.geekbang.common.auth;

import com.cz.spring_boot_netty_demo.geekbang.common.Operation;
import com.cz.spring_boot_netty_demo.geekbang.common.OperationResult;
import lombok.Data;
import lombok.extern.java.Log;

@Log
@Data
public class AuthOperation extends Operation {

    private final String userName;
    private final String password;

    private final static String ADMIN = "admin";
    @Override
    public AuthOperationResult execute() {
        if(ADMIN.equalsIgnoreCase(this.password)){
            return new AuthOperationResult(true);
        }
        return new AuthOperationResult(false);
    }
}
