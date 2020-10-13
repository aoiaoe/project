package com.cz.springcloudconsumerfeign.feign;

import com.cz.springcloud.api.EntityApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author alian
 * @date 2020/10/13 下午 12:07
 * @since JDK8
 */
@FeignClient(name = "provider")
public interface EntityFeignClient extends EntityApi {
}
