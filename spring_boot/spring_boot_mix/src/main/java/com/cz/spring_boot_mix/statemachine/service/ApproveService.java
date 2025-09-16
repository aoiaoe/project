package com.cz.spring_boot_mix.statemachine.service;

import com.cz.spring_boot_mix.statemachine.ApproveTaskHolder;
import com.cz.spring_boot_mix.statemachine.entity.ApproveTask;
import com.cz.spring_boot_mix.statemachine.event.ApproveEvent;
import com.cz.spring_boot_mix.statemachine.state.ApproveStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ApproveService {

    @Autowired
    private StateMachine<ApproveStates, ApproveEvent> stateMachine;

    public void aiPreApproveTask(Long id) {
        log.info("task id: {}", id);
        ApproveTask task = ApproveTaskHolder.getApproveTask(id);
//        payService.payOrder(1);
        Message<ApproveEvent> message = MessageBuilder.withPayload(ApproveEvent.AI_APPROVED)
                .setHeader("task", task).build();
        stateMachine.sendEvent(Mono.just(message)).subscribe();
    }

    public void manualApproved(Long id) {
        log.info("task id: {}", id);
        ApproveTask task = ApproveTaskHolder.getApproveTask(id);
        Message<ApproveEvent> message = MessageBuilder.withPayload(ApproveEvent.MANUAL_APPROVED)
                .setHeader("task", task).build();
        stateMachine.sendEvent(Mono.just(message)).subscribe();
    }

    public void leaderReject(Long id) {
        log.info("task id: {}", id);
        ApproveTask task = ApproveTaskHolder.getApproveTask(id);
//        payService.payOrder(1);
        Message<ApproveEvent> message = MessageBuilder.withPayload(ApproveEvent.LEADER_REJECT)
                .setHeader("task", task).build();
        stateMachine.sendEvent(Mono.just(message)).subscribe();
    }

    public void leaderSuccess(Long id) {
        log.info("task id: {}", id);
        ApproveTask task = ApproveTaskHolder.getApproveTask(id);
//        payService.payOrder(1);
        Message<ApproveEvent> message = MessageBuilder.withPayload(ApproveEvent.LEADER_APPROVE_SUCCESS)
                .setHeader("task", task).build();
        stateMachine.sendEvent(Mono.just(message)).subscribe();
    }

    public void leaderFail(Long id) {
        log.info("task id: {}", id);
        ApproveTask task = ApproveTaskHolder.getApproveTask(id);
//        payService.payOrder(1);
        Message<ApproveEvent> message = MessageBuilder.withPayload(ApproveEvent.LEADER_APPROVE_SUCCESS)
                .setHeader("task", task).build();
        stateMachine.sendEvent(Mono.just(message)).subscribe();
    }
}
