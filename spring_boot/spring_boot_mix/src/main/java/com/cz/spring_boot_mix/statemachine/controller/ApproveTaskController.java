package com.cz.spring_boot_mix.statemachine.controller;

import com.cz.spring_boot_mix.statemachine.ApproveTaskHolder;
import com.cz.spring_boot_mix.statemachine.entity.ApproveTask;
import com.cz.spring_boot_mix.statemachine.service.ApproveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("approve")
@RestController
public class ApproveTaskController {

    @Autowired
    private ApproveService approveService;

    @GetMapping("/list")
    public Map<Long, ApproveTask> list() {
        return ApproveTaskHolder.getDataHolder();
    }

    @GetMapping("/ai/{id}")
    public String payOrder(@PathVariable("id") Long id) {
        approveService.aiPreApproveTask(id);
        return "ai approved ";

    }

    @GetMapping("/manual/{id}")

    public String shipOrder(@PathVariable("id") Long id) {
        approveService.manualApproved(id);
        return "manual approved ";

    }

    @GetMapping("/leader/reject/{id}")
    public String leaderReject(@PathVariable("id") Long id) {
        approveService.leaderReject(id);
        return "leader rejected ";
    }

    @GetMapping("/leader/success/{id}")
    public String leaderSuccess(@PathVariable("id") Long id) {
        approveService.leaderSuccess(id);
        return "leader rejected ";
    }

    @GetMapping("/leader/faul/{id}")
    public String leaderFail(@PathVariable("id") Long id) {
        approveService.leaderFail(id);
        return "leader rejected ";
    }
}
