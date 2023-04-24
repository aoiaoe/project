package com.geekbang.designpattern._06_reponsibility_chain;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jzm
 * @date 2023/4/24 : 10:22
 */
@Slf4j
public class BHandler extends Handler {
    @Override
    protected boolean doHandle(Object param) {
        log.info("B处理器处理成功");
        return true;
    }
}
