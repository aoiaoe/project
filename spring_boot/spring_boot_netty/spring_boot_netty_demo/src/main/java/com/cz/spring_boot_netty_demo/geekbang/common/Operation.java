package com.cz.spring_boot_netty_demo.geekbang.common;

public abstract class Operation extends MsgBody {
    public abstract OperationResult execute();
}
