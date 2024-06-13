package com.cz.spring_cloud_dubbo_consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cz.spring_cloud_dubbo_intf.DubboService;
import org.apache.skywalking.apm.toolkit.trace.ActiveSpan;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jzm
 * @date 2023/5/29 : 16:31
 */
@RequestMapping(value = "/hello")
@RestController
public class HelloController {

    @Reference(version = "1.0.0")
    private DubboService dubboService;

    @GetMapping(value = "/hello/{words}")
    public String world(@PathVariable String words) throws Exception {
//        Thread.sleep(1000);
//        System.out.println("traceId" + TraceContext.traceId());
//        ActiveSpan.tag("hello-trace activation", words);
        String result = this.dubboService.sayHello(words);
//        Thread.sleep(1000);
        return result;
    }

    @GetMapping(value = "/err")
    public String err(){
        System.out.println("traceId" + TraceContext.traceId());
        ActiveSpan.tag("hello-trace activation", "error");
        throw new RuntimeException("error");
    }
}
