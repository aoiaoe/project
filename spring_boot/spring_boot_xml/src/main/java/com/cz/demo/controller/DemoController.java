package com.cz.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class DemoController {

    @GetMapping(value = "/data", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    public Data createData(@RequestBody Data data){
        System.out.println(data);
        data.setId(1123123L);
        data.setName("czasfsaat");
        return data;
    }



}
