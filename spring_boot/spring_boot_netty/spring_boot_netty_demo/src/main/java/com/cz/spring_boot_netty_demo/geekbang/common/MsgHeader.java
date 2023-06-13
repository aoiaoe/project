package com.cz.spring_boot_netty_demo.geekbang.common;

import lombok.Data;

@Data
public class MsgHeader {

    private int version = 1;

    private Long streamId;

    private int opCode;

}
