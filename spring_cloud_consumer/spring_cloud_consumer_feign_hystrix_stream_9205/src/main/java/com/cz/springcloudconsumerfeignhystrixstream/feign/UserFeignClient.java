package com.cz.springcloudconsumerfeignhystrixstream.feign;

import com.cz.springcloud.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user")
public interface UserFeignClient extends UserApi {
}
