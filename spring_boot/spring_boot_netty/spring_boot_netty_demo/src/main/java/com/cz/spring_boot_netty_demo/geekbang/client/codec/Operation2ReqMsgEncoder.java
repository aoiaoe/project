package com.cz.spring_boot_netty_demo.geekbang.client.codec;

import com.cz.spring_boot_netty_demo.geekbang.common.Operation;
import com.cz.spring_boot_netty_demo.geekbang.common.RequestMsg;
import com.cz.spring_boot_netty_demo.geekbang.util.IdUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class Operation2ReqMsgEncoder extends MessageToMessageEncoder<Operation> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Operation op, List<Object> out) throws Exception {
        RequestMsg msg = new RequestMsg(IdUtil.nextId(), op);
        out.add(msg);
    }
}
