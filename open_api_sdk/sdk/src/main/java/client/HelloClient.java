package client;

import api.AppRequest;
import com.alibaba.fastjson.JSON;
import dto.HelloDto;
import enums.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import vo.HelloVo;

@Slf4j
public class HelloClient extends ClientAbstract {

    public static HelloVo queryUserList(AppRequest<HelloDto> request) {
        try {
            String str = post(request);
            return JSON.parseObject(str, HelloVo.class);
        } catch (Exception e) {
            log.error("SysUserClient queryUserList is exception! request={}", request);
            e.printStackTrace();
            return null;
        }
    }
}
