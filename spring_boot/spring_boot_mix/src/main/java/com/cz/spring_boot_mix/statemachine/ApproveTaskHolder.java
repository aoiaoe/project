package com.cz.spring_boot_mix.statemachine;

import com.cz.spring_boot_mix.statemachine.entity.ApproveTask;
import com.cz.spring_boot_mix.statemachine.state.ApproveStates;

import java.util.HashMap;
import java.util.Map;

public class ApproveTaskHolder {

    private static final Map<Long, ApproveTask> DATA_HOLDER = new HashMap<Long, ApproveTask>();

    static {
        DATA_HOLDER.put(1L, new ApproveTask(1L, ApproveStates.NEW, "任务1"));
        DATA_HOLDER.put(2L, new ApproveTask(2L, ApproveStates.MANUAL_APPROVE, "任务2"));
        DATA_HOLDER.put(3L, new ApproveTask(3L, ApproveStates.LEADER_APPROVE, "任务3"));
    }

    public static ApproveTask getApproveTask(Long id) {
        ApproveTask task = DATA_HOLDER.get(id);
        if (task == null) {
            throw new RuntimeException("task not exist, id:" + id);
        }
        return task;
    }

    public static void updateApproveTask(ApproveTask task) {
        DATA_HOLDER.put(task.getId(), task);
    }

    public static Map<Long, ApproveTask> getDataHolder() {
        return DATA_HOLDER;
    }
}
