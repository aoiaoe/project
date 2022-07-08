package com.cz.spring_boot_template.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jzm
 * @date 2022/7/7 : 14:48
 */
@Controller
@RequestMapping(value = "/freemarker")
public class FreemarkerController {

    @GetMapping
    public String index(Model model){
        return "HelloFreemarker";
    }
}
