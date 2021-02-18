package com.cz.springcloudprovideruser.feign;

import com.cz.springcloud.api.EntityApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "provider")
public interface EntityFeignClient extends EntityApi {
}
