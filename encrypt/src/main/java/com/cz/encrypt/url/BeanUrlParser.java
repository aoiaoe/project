package com.cz.encrypt.url;

import com.cz.encrypt.bean.BizBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author jiaozi《liaomin @ gvt861.com》
 * @since JDK8
 * Creation time：2019/8/15 17:58
 */
@Slf4j
public class BeanUrlParser<T extends BizBean> implements UrlParser {
    private T bean;

    public BeanUrlParser(T bean) {
        this.bean = bean;
    }

    private boolean isBaseType(Object object) {
        Class className = object.getClass();
        if (className.equals(String.class) ||
                className.equals(Integer.class) ||
                className.equals(Byte.class) ||
                className.equals(Long.class) ||
                className.equals(Double.class) ||
                className.equals(Float.class) ||
                className.equals(Character.class) ||
                className.equals(Short.class) ||
                className.equals(Boolean.class)) {
            return true;
        }
        return false;
    }

    public String urlMap(String parentKey, Object obj) {
        StringBuffer paramUrl = new StringBuffer();
        Method[] methods = obj.getClass().getDeclaredMethods();
        //将字段
        Arrays.stream(methods).forEach(method -> {
            boolean ifGet = method.getName().startsWith("get");
            if (ifGet) {
                String key = method.getName().split("get")[1];
                key = key.substring(0, 1).toLowerCase() + key.substring(1);
                Object value = null;
                try {
                    value = method.invoke(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (value != null && isBaseType(value)) {
                    if (StringUtils.isNotEmpty(value.toString()) && !"null".equalsIgnoreCase(value.toString())) {
                        paramUrl.append((parentKey == null ? key : (parentKey + "_" + key)) + "=" + value + "&");
                    }
                } else if (method.getReturnType().equals(Map.class)) {
                    Map<String, Object> ctreeMap = new TreeMap<String, Object>();
                    ctreeMap.putAll((Map) value);
                    paramUrl.append(urlMap(key, ctreeMap));
                } else if (method.getReturnType().equals(List.class)) {
                    List mapList = (List) value;
                    for (int i = 0; i < mapList.size(); i++) {
                        Map cmap = (Map) mapList.get(i);
                        Map<String, Object> ctreeMap = new TreeMap<String, Object>();
                        ctreeMap.putAll(cmap);
                        paramUrl.append(urlMap(key + "[" + i + "]", ctreeMap));
                    }
                } else if (method.getReturnType().getSimpleName().equalsIgnoreCase("byte[]")) {
                    try {
                        if (value != null) {
                            byte[] vabyte = (byte[]) value;
                            if (vabyte.length > 0) {
                                value = md5(vabyte);
                                if (StringUtils.isNotEmpty(value.toString()) && !"null".equalsIgnoreCase(value.toString())) {
                                    paramUrl.append((parentKey == null ? key : (parentKey + "_" + key)) + "=" + value + "&");
                                }
                            }
                        }
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                } else if (method.getReturnType().equals(File.class)) {
                    try {
                        byte[] bytes = IOUtils.toByteArray(new FileInputStream((File) value));
                        value = md5(bytes);
                        if (value != null && StringUtils.isNotEmpty(value.toString()) && !"null".equalsIgnoreCase(value.toString())) {
                            paramUrl.append((parentKey == null ? key : (parentKey + "_" + key)) + "=" + value + "&");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (value != null) {
                        paramUrl.append(urlMap(key, value));
                    }
                }
            }
        });


        return paramUrl.toString();
    }

    private String md5(byte[] bt) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        return Base64.getEncoder().encodeToString(messageDigest.digest(bt));
    }

    @Override
    public String parse() {
        String returnUrl = urlMap(null, bean);
        if (returnUrl.toString().endsWith("&")) {
            returnUrl = returnUrl.toString().substring(0, returnUrl.length() - 1);
            log.debug(returnUrl);
            return returnUrl;
        }
        log.debug(returnUrl.toString());
        return returnUrl;
    }
}
