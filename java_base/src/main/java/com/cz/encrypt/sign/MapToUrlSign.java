package com.cz.encrypt.sign;


import com.cz.encrypt.url.MapUrlParser;
import com.cz.encrypt.url.UrlParser;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Map;
import java.util.TreeMap;

/**
 * 将集合M内非空参数值的参数按照参数名ASCII码从小到大排序（字典序）格式（即key1=value1&amp;key2=value2…）拼接成字符串stringA。
 * @author jiaozi《liaomin艾特gvt861.com》
 * @since JDK8
 * Creation time：2019/8/8 14:04
 */
@Slf4j
public class MapToUrlSign extends AbstractUrlSign {
    private Map<String,Object> jsonMap;
    private Key key;
    public static final String SIGN_KEY="sign";
    public static final String APPID_KEY="appid";
    private UrlParser urlParser;
    public MapToUrlSign(Key key, Map<String,Object> jsonMap) {
        Map<String, Object> treeMap = new TreeMap<String, Object>();
        treeMap.putAll(jsonMap);
        this.jsonMap = treeMap;
        this.key = key;
        urlParser=new MapUrlParser(this.jsonMap);
    }

    @Override
    public Object data() {
        return this.jsonMap;
    }

    @Override
    public Key getKey() {
        return this.key;
    }

    @Override
    public String url() {
        StringBuffer paramUrl=new StringBuffer();
        return urlParser.parse();
    }

    @Override
    public boolean allowdSign() {
        if(!this.jsonMap.containsKey(APPID_KEY)) {
            return false;
        }
        return true;
    }
}
