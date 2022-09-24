package com.cz.spring_boot_mix.contoller;

import com.cz.spring_boot_mix.scheduled.CronSchedulingConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/scheduling")
@RestController
public class SchedulerController {

    @Qualifier("cronSchedulingConfig")
    @Autowired
    private CronSchedulingConfig cronSchedulingConfig;

    @PostMapping
    public boolean update(String cron, Long timer){
         // crom校验
        if(cron != null){
            cronSchedulingConfig.setCron(cron);
        }
        if(timer != null){
            cronSchedulingConfig.setTimer(timer);
        }
        return  true;
    }
}
