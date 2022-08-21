package com.cz.spring_boot_security_yd02_7011.config;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

@RequestMapping(value = "/hello")
@RestController
public class HelloController {

    @GetMapping(value = "/all")
    public String all(){
        return "all";
    }

    @GetMapping(value = "/admin")
    public String admin(){
        return "admin";
    }

    @GetMapping(value = "/user")
    public String user(){
        return "user";
    }

    @PermitAll
    @GetMapping(value = "/anno/all")
    public String annotationAll(){
        return "annotationAll";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/anno/admin")
    public String annotationAdmin(){
        return "annotationAdmin";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping(value = "/anno/user")
    public String annotationUser(){
        return "annotationUser";
    }

}
