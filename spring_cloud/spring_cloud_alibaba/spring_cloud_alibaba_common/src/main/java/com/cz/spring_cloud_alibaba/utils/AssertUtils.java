package com.cz.spring_cloud_alibaba.utils;

import com.cz.spring_cloud_alibaba.enums.ErrorEnums;
import com.cz.spring_cloud_alibaba.exception.BizException;

/**
 * @author jzm
 * @date 2022/10/9 : 15:33
 */
public class AssertUtils {

    public static void notNull(Object obj, ErrorEnums error){
        if(obj == null){
            throw BizException.error(error);
        }
    }

    public static void isTrue(boolean flag, ErrorEnums error){
        if(!flag){
            throw BizException.error(error);
        }
    }
}
