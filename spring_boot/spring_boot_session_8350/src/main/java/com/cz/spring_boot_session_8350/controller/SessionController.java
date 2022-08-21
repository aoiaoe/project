package com.cz.spring_boot_session_8350.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RequestMapping(value = "/session")
@RestController
public class SessionController {

    @GetMapping(value = "/set")
    public boolean setValue(HttpSession session, String key, String value){
        session.setAttribute(key, value);
        return true;
    }

    @GetMapping(value = "/getAll")
    public Map getValue(HttpSession session){
        Map<String, Object> result = new HashMap<>();
        // 遍历
        for (Enumeration<String> enumeration = session.getAttributeNames();
             enumeration.hasMoreElements();) {
            String key = enumeration.nextElement();
            Object value = session.getAttribute(key);
            result.put(key, value);
        }
        // 返回
        return result;
    }
}
