package com.cz.open_api_sdk_server.utils;

/**
 * @author jzm
 * @date 2022/6/7 : 11:25
 */
public class ParamHolder {

    private static ThreadLocal<byte[]> PARAM_HOLDER = new ThreadLocal<>();

    public static void set(byte[] param){
        PARAM_HOLDER.set(param);
    }

    public static byte[] get(){
        return PARAM_HOLDER.get();
    }

    public static void clear(){
        PARAM_HOLDER.remove();
    }
}
