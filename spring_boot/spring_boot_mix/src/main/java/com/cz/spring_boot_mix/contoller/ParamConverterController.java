package com.cz.spring_boot_mix.contoller;

import com.cz.spring_boot_mix.domain.ParamConverterDomain;
import com.cz.spring_boot_mix.paramresolver.MyRequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/test")
@RestController
public class ParamConverterController {

    @PostMapping("/paramResolver")
    public ParamConverterDomain test(@RequestBody ParamConverterDomain param){
        return param;
    }
}
