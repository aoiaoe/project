package com.cz.spring_boot_mix.config;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 *
 */
@ControllerAdvice
public class MyInitBinder {

    /**
     * 参数绑定器，配置方法的返回值必须为void
     *      1.如果想针对全局,则必须放入@ControllerAdvice注解的类中
     *      2.如果只针对某个Controller可以将此方法放入指定Controller中
     *
     *
     * @param webDataBinder
     */
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

}
