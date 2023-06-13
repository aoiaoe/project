package com.cz.spring_boot_netty_demo.geekbang.common;

public class RequestMsg extends Msg<Operation> {
    @Override
    public Class getMsgBodyDecodeClass(int opCode) {
        return OperationType.fromOpCode(opCode).getOperationClass();
    }

    public RequestMsg() {
    }

    public RequestMsg(Long streamId, Operation operation) {
        MsgHeader header = new MsgHeader();
        header.setStreamId(streamId);
        header.setOpCode(OperationType.fromOperation(operation).getOpCode());
        this.setMsgHeader(header);
        this.setMsgBody(operation);
    }
}
