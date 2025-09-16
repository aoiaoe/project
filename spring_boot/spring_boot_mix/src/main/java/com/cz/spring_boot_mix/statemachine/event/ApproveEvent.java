package com.cz.spring_boot_mix.statemachine.event;

public enum ApproveEvent {

    AI_APPROVED, // AI审核完成
    MANUAL_APPROVED, // 人工审核完成
    LEADER_REJECT, // 打回
    LEADER_APPROVE_FAIL, // 拒绝
    LEADER_APPROVE_SUCCESS // 同意
}
