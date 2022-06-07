package com.cz.open_api_sdk_server.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.cz.open_api_sdk_server.enums.ResponseCode;
import com.cz.open_api_sdk_server.exception.ApiException;
import com.cz.open_api_sdk_server.utils.ParamHolder;
import com.cz.open_api_sdk_server.utils.RSAUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author jzm
 * @date 2022/6/7 : 10:39
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {
    public static final String VERSION_FIELD = "version";
    public static final String SIGN_FIELD = "sign";
    public static final String APPID_FIELD = "appid";
    public static final String APPLICATION_TYPE = "application/json";
    public static final String CONTENT_TYPE_KEY = "Content-Type";
    public static final String REQUEST_GET = "GET";
    public static final String APPLICATION_ZIP = "application/zip";
    public final String DATA_VALUE = "data";
    public final String JSON = "";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String contentType = request.getHeader(CONTENT_TYPE_KEY);
        if(contentType.toLowerCase().contains(APPLICATION_TYPE)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String body = IOUtils.toString(reader);
            JSONObject jsonObject = JSONObject.parseObject(body);
            String data = jsonObject.getString(DATA_VALUE);
            if (StringUtils.isBlank(data)){
               throw new ApiException(ResponseCode.PARAM_ERROR);
            }
            byte[] bytes = RSAUtil.privateDecrypt(data, RSAUtil.PRIVATE_KEY);
            if(bytes == null) {
                throw new ApiException(ResponseCode.API_DECRYPT_ERROR);
            }
            ParamHolder.set(bytes);
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        ParamHolder.clear();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ParamHolder.clear();
    }
}
