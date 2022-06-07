package client;

import api.ApiRequest;
import api.ApiResponse;
import api.AppRequest;
import com.alibaba.fastjson.JSON;
import enums.ResponseCode;
import exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import utils.HttpUtil;
import utils.RSAUtil;

@Slf4j
class ClientAbstract {

    static String post(AppRequest request) {

        ApiRequest hopeRequest = ApiRequest.builder()
                .appId(request.getAppId())
                .data(RSAUtil.encryptByPubKey(request.getPublicKey(), JSON.toJSONString(request.getData())))
                .build();
        String s = HttpUtil.doPost(request.getUrl(), JSON.toJSONString(hopeRequest));
        if (StringUtils.isBlank(s)) {
            log.error("client post api result is null!");
            throw new ApiException(ResponseCode.API_ERROR);
        }
        ApiResponse hopeResponse = JSON.parseObject(s, ApiResponse.class);
        if (!hopeResponse.isSuccess()) {
            log.error("client post api error! hopeResponse={}", hopeResponse);
            throw new ApiException(ResponseCode.API_ERROR.getCode(), hopeResponse.getMsg());
        }
        return RSAUtil.decryptByPubKey(request.getPublicKey(), hopeResponse.getData());
    }
}
