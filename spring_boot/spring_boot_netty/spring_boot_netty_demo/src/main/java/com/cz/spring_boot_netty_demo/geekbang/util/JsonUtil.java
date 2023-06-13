package com.cz.spring_boot_netty_demo.geekbang.util;

import com.alibaba.fastjson.JSONObject;
import com.cz.spring_boot_netty_demo.geekbang.common.MsgBody;

public class JsonUtil {

    public static String toJson(MsgBody msgBody){
        return JSONObject.toJSONString(msgBody);
    }

    public static <T extends MsgBody> T parse(String body, Class<T> bodyClazz) {
        return JSONObject.parseObject(body, bodyClazz);
    }
}
