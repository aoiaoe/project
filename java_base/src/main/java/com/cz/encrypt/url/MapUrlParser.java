package com.cz.encrypt.url;

import com.cz.encrypt.sign.MapToUrlSign;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author jiaozi《liaomin@gvt861.com》
 * @since JDK8
 * Creation time：2019/8/13 13:14
 */
@Slf4j
public class MapUrlParser implements UrlParser {
    private Map<String,Object> dataMap=new TreeMap<>();

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public MapUrlParser(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }

    @Override
    public String parse() {
        String returnUrl=urlMap(null,dataMap);
        if(returnUrl.toString().endsWith("&")){
            returnUrl=returnUrl.toString().substring(0,returnUrl.length()-1);
            log.debug(returnUrl);
            return returnUrl;
        }
        log.debug(returnUrl.toString());
        return returnUrl;
    }
    public String urlMap(String parentKey,Map<String,Object> map){
        StringBuffer paramUrl=new StringBuffer();
        map.entrySet().forEach(entry->{
            if(!MapToUrlSign.SIGN_KEY.equals(entry.getKey())) {
                Object value = entry.getValue();
                if (value instanceof Map) {
                    Map<String, Object> ctreeMap = new TreeMap<String, Object>();
                    ctreeMap.putAll((Map) value);
                    paramUrl.append(urlMap(entry.getKey(),ctreeMap));
                }else if (value instanceof List) {
                    List mapList=(List)value;
                    for (int i = 0; i < mapList.size(); i++) {
                        Map cmap = (Map) mapList.get(i);
                        Map<String, Object> ctreeMap = new TreeMap<String, Object>();
                        ctreeMap.putAll(cmap);
                        paramUrl.append(urlMap(entry.getKey()+"["+i+"]",ctreeMap));
                    }
                } else {
                    if(entry.getValue()!=null&& StringUtils.isNotEmpty(entry.getValue().toString()) && !"null".equalsIgnoreCase(entry.getValue().toString())){
                        paramUrl.append((parentKey==null?entry.getKey():(parentKey+"_"+entry.getKey())) + "=" + entry.getValue() + "&");

                    }
                }
            }
        });

        return paramUrl.toString();
    }
}
