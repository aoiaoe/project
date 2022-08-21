package com.cz.springcloudconsumerfeign.feign;

import com.cz.springcloud.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 结合FeignFactory使用
 *
 * @author alian
 * @date 2020/11/12 下午 4:29
 * @since JDK8
 */
public interface UserClient {


    @GetMapping(value = "/user/list")
    List<User> findAll();

    @GetMapping(value = "/user/{id}")
    User get(@PathVariable("id") Long id);
}
