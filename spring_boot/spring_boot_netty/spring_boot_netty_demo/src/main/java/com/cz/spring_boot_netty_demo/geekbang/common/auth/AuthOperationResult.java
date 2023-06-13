package com.cz.spring_boot_netty_demo.geekbang.common.auth;

import com.cz.spring_boot_netty_demo.geekbang.common.OperationResult;
import lombok.Data;

@Data
public class AuthOperationResult extends OperationResult {

    private final boolean passAuth;
}

