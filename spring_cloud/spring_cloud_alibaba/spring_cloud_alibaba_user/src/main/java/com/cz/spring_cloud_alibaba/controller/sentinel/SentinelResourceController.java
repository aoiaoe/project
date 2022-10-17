package com.cz.spring_cloud_alibaba.controller.sentinel;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.cz.spring_cloud_alibaba.controller.sentinel.blockhandler.ResourceBlockHandler;
import com.cz.spring_cloud_alibaba.domain.Data;
import com.cz.spring_cloud_alibaba.service.SentinelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/resource")
@RestController
public class SentinelResourceController {

    @Autowired
    private SentinelService sentinelService;


    /**
     * 需要在sentinel dashboard的 '流控规则' 菜单中添加相关的流控规则
     * blockHandler为兜底方法，可以存在于当前类中(可忽略blockHandlerClass)， 可以
     *      不存在于当前类(必需blockHandlerClass属性指定类，方法必须为static)
     * @return
     */
    @SentinelResource(value = "entrance",
            blockHandler = "entrance",
            blockHandlerClass = ResourceBlockHandler.class)
    @GetMapping(value = "/entrance")
    public Data entrance(){
        return Data.builder().data(sentinelService.end("entrance")).build();
    }

}
