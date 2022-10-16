package com.cz.spring_cloud_alibaba.stream;

import com.alibaba.fastjson.JSON;
import com.cz.spring_cloud_alibaba.domain.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * @author jzm
 * @date 2022/10/13 : 16:45
 */
@Slf4j
@Component
public class UserVoMessageListener implements Consumer<UserVo> {

    @Override
    public void accept(UserVo userVo) {
        log.info("spring cloud stream 从rabbitmq中获取数据 {}", JSON.toJSONString(userVo));
    }
}

@Slf4j
@Component
class UserVoMessageListener2 implements Consumer<UserVo> {

    @Override
    public void accept(UserVo userVo) {
        log.info("spring cloud stream 从rabbitmq中获取数据 {}", JSON.toJSONString(userVo));
    }
}
