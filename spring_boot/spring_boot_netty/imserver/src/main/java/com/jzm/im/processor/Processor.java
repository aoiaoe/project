package com.jzm.im.processor;

import com.jzm.common.bean.msg.ProtoMsg;
import com.jzm.im.session.ServerSession;

public interface Processor {

    ProtoMsg.HeadType getType();
    boolean process(ServerSession session, ProtoMsg.Message msg);
}
