package com.cz.learnnetty.rpc.message;

import com.cz.learnnetty.chatroom.message.Message;
import lombok.Data;

@Data
public class RpcRequestMessage extends Message {
    @Override
    public int getMsgType() {
        return 0;
    }

    private String interfaceName;

    private String method;

    private Class<?> returnType;

    private Class[] parametersCLz;

    private Object[] parameters;

    public RpcRequestMessage(int seqId, String interfaceName, String method, Class<?> returnType, Class[] parametersCLz, Object[] parameters) {
        super.setSeqId(seqId);
        this.interfaceName = interfaceName;
        this.method = method;
        this.returnType = returnType;
        this.parametersCLz = parametersCLz;
        this.parameters = parameters;
    }
}
