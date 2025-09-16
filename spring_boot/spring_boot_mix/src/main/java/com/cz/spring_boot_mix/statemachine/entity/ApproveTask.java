package com.cz.spring_boot_mix.statemachine.entity;

import com.cz.spring_boot_mix.statemachine.state.ApproveStates;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApproveTask {

    private Long id;

    private ApproveStates approveStates;

    private String taskName;
}
