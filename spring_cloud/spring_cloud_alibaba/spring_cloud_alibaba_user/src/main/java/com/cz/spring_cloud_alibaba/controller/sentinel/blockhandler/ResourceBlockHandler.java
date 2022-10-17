package com.cz.spring_cloud_alibaba.controller.sentinel.blockhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.cz.spring_cloud_alibaba.domain.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jzm
 * @date 2022/10/17 : 17:24
 */
@Slf4j
public class ResourceBlockHandler {

    public static Data entrance(BlockException blockException){
        log.error("[流量控制] 降级：{}", blockException);
        return Data.builder().data("blocked by sentinel").build();
    }

}
