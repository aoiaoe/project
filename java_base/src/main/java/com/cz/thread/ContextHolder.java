package com.cz.thread;

/**
 * @author jzm
 * @date 2022/9/21 : 13:58
 */
public class ContextHolder {

    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static void set(String context){
        CONTEXT.set(context);
    }

    public static String get(){
        return CONTEXT.get();
    }

    public static void clear(){
        CONTEXT.remove();
    }
}
