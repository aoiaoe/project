package com.cz.spring_boot_mix.statemachine.handler;

import com.cz.spring_boot_mix.statemachine.ApproveTaskHolder;
import com.cz.spring_boot_mix.statemachine.entity.ApproveTask;
import com.cz.spring_boot_mix.statemachine.event.ApproveEvent;
import com.cz.spring_boot_mix.statemachine.state.ApproveStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@WithStateMachine
public class ApproveStateHandler {

    @OnTransition(source = "NEW", target = "MANUAL_APPROVE")
    public void aiPreApprove(Message<ApproveEvent> message) {
        ApproveTask task = (ApproveTask) message.getHeaders().get("task");
        log.info("Handle ai pre approve task：{}", task);
        // 其他业务 如保存订单状态
        Objects.requireNonNull(task).setApproveStates(ApproveStates.MANUAL_APPROVE);
        //orderRepository.save(order);
        ApproveTaskHolder.updateApproveTask(task);
    }

    @OnTransition(source = "MANUAL_APPROVE", target = "LEADER_APPROVE")
    public void manualApprove(Message<ApproveEvent> message) {
        ApproveTask task = (ApproveTask) message.getHeaders().get("task");
        log.info("Handle manual approve task：{}", task);
        // 其他业务 如更新订单
        Objects.requireNonNull(task).setApproveStates(ApproveStates.LEADER_APPROVE);
        // orderMapper.updateById(order);
        ApproveTaskHolder.updateApproveTask(task);
    }

    @OnTransition(source = "LEADER_APPROVE", target = "MANUAL_APPROVE")
    public void leaderApproveReject(Message<ApproveEvent> message) {
        ApproveTask task = (ApproveTask) message.getHeaders().get("task");
        log.info("Handle leader reject task：{}", task);
        // 其他业务 如更新订单
        Objects.requireNonNull(task).setApproveStates(ApproveStates.MANUAL_APPROVE);
        // orderMapper.updateById(order);
        ApproveTaskHolder.updateApproveTask(task);
    }

    @OnTransition(source = "LEADER_APPROVE", target = "SUCCESS")
    public void leaderApproveSuccess(Message<ApproveEvent> message) {
        ApproveTask task = (ApproveTask) message.getHeaders().get("task");
        log.info("Handle leader approve success task：{}", task);
        // 其他业务 如更新订单
        Objects.requireNonNull(task).setApproveStates(ApproveStates.SUCCESS);
        // orderMapper.updateById(order);
        ApproveTaskHolder.updateApproveTask(task);
    }

    @OnTransition(source = "LEADER_APPROVE", target = "FAIL")
    public void leaderApproveFail(Message<ApproveEvent> message) {
        ApproveTask task = (ApproveTask) message.getHeaders().get("task");
        log.info("Handle leader approve fail task：{}", task);
        // 其他业务 如更新订单
        Objects.requireNonNull(task).setApproveStates(ApproveStates.FAIL);
        // orderMapper.updateById(order);
        ApproveTaskHolder.updateApproveTask(task);
    }
}
