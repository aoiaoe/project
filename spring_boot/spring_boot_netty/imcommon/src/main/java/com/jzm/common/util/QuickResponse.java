package com.jzm.common.util;

import com.jzm.common.bean.msg.ProtoMsg;
import com.jzm.common.constant.ProtocolConstants;
import io.netty.channel.ChannelHandlerContext;

public class QuickResponse {

    public static ProtoMsg.Message buildLogResponse(
            ProtocolConstants.ResultCodeEnum en, long seqId, String sessionId) {

        ProtoMsg.Message.Builder outer = ProtoMsg.Message.newBuilder()
                .setType(ProtoMsg.HeadType.LOGIN_RESPONSE)  //设置消息类型
                .setSequence(seqId)
                .setSessionId(sessionId);  //设置应答流水，与请求对应

        ProtoMsg.LoginResponse.Builder b = ProtoMsg.LoginResponse.newBuilder()
                .setCode(en.getCode())
                .setInfo(en.getDesc())
                .setExpose(1);

        outer.setLoginResponse(b.build());
        return outer.build();
    }

}
