package com.cz.spring_boot_mix.contoller.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "dummy", url = "http://121.4.79.86:8089")
public interface OpenrestyFeignClient {

    @GetMapping(value = "/json")
    String getStr();
}
