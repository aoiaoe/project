package com.cz.learnnetty.rpc.message;

import com.cz.learnnetty.chatroom.message.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RpcResponseMessage extends Message {

    private Object returnValue;

    private Exception exception;

    @Override
    public int getMsgType() {
        return RpcResponseMessage;
    }
}
