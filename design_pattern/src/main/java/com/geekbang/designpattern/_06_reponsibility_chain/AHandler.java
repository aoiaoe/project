package com.geekbang.designpattern._06_reponsibility_chain;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jzm
 * @date 2023/4/24 : 10:21
 */
@Slf4j
public class AHandler extends Handler{
    @Override
    protected boolean doHandle(Object param) {
        log.info("A处理器处理失败");
        return false;
    }
}
