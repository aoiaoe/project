package com.cz.spring_boot_template.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author jzm
 * @date 2022/7/7 : 14:48
 */
@Controller
@RequestMapping(value = "/freemarker")
public class FreemarkerController {


    @GetMapping(value = "login")
    public String login(Model model){
        return "Login";
    }

    @PostMapping(value = "login")
    public String login1(String userName, String password, RedirectAttributes model){
        model.addFlashAttribute("token", "tokenAAABBB");
        return "redirect:/freemarker";
    }

    @GetMapping
    public String index(String token, Model model){
        return "HelloFreemarker";
    }
}
