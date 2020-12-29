package com.cz.spring_boot_test.controller;

import com.cz.spring_boot_test.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/map")
@RestController
public class MapController {

    @Autowired
    private MapService mapService;

    @GetMapping(value = "/{key}")
    public String getValue(@PathVariable(value = "key") String key){
        return this.mapService.getValue(key);
    }

}
