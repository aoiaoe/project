package com.cz.spring_boot_netty_demo.geekbang.server.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 帧编码器:处理TCP粘包和半包问题
 */
public class OrderFrameDecode extends LengthFieldBasedFrameDecoder {
    public OrderFrameDecode() {
        super(Integer.MAX_VALUE, 0, 2, 0, 2);
    }
}
