package com.cz.spring_boot_mix.websocket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebSocketIndexController {

    @GetMapping(value = "/ws/index.html")
    public String index(){
        return "index";
    }
}
