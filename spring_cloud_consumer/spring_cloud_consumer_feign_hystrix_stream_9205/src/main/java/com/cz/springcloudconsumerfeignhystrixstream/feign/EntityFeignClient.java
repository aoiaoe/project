package com.cz.springcloudconsumerfeignhystrixstream.feign;

import com.cz.springcloud.api.EntityApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "provider")
public interface EntityFeignClient extends EntityApi {
}
