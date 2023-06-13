package com.cz.spring_boot_netty_demo.geekbang.common;

public class ResponseMsg extends Msg<OperationResult>{
    @Override
    public Class getMsgBodyDecodeClass(int opCode) {
        return OperationType.fromOpCode(opCode).getOperationResultClass();
    }
}
