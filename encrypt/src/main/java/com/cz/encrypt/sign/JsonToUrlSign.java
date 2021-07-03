package com.cz.encrypt.sign;

import com.alibaba.fastjson.JSONObject;

import java.security.Key;
import java.util.Map;

/**
 * json格式数据签名
 * {
 * appid:34324535,
 * secrect:redfg564564,
 * mch_id:11,
 * hash:34546456
 * }
 *
 * @author jiaozi《liaomin艾特gvt861.com》
 * @since JDK8
 * Creation time：2019/8/8 12:15
 */
public class JsonToUrlSign extends MapToUrlSign {
    private String jsonData;
    private Key privateKey;

    public JsonToUrlSign(Key privateKey, String jsonData) {
        super(privateKey, JSONObject.parseObject(jsonData, Map.class));
    }
}
