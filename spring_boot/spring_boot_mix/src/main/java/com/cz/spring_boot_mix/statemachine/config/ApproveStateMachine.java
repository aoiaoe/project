package com.cz.spring_boot_mix.statemachine.config;

import com.cz.spring_boot_mix.statemachine.entity.ApproveTask;
import com.cz.spring_boot_mix.statemachine.event.ApproveEvent;
import com.cz.spring_boot_mix.statemachine.state.ApproveStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.*;

@Slf4j
@Configuration
@EnableStateMachine
public class ApproveStateMachine extends StateMachineConfigurerAdapter<ApproveStates, ApproveEvent> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<ApproveStates, ApproveEvent> config) throws Exception {
        config.withConfiguration()
                .autoStartup(true)
                .listener(listener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<ApproveStates, ApproveEvent> states) throws Exception {
        states.withStates()
                .initial(ApproveStates.NEW)
                .end(ApproveStates.FAIL)
                .end(ApproveStates.SUCCESS)
                .states(EnumSet.allOf(ApproveStates.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<ApproveStates, ApproveEvent> transitions) throws Exception {
        transitions.withExternal()
                .source(ApproveStates.NEW).target(ApproveStates.MANUAL_APPROVE).event(ApproveEvent.AI_APPROVED)
                .and()
                .withExternal()
                .source(ApproveStates.MANUAL_APPROVE).target(ApproveStates.LEADER_APPROVE).event(ApproveEvent.MANUAL_APPROVED)
                .and()
                .withExternal()
                .source(ApproveStates.LEADER_APPROVE).target(ApproveStates.MANUAL_APPROVE).event(ApproveEvent.LEADER_REJECT)
                .and()
                .withExternal()
                .source(ApproveStates.LEADER_APPROVE).target(ApproveStates.SUCCESS).event(ApproveEvent.LEADER_APPROVE_SUCCESS)
                .and()
                .withExternal()
                .source(ApproveStates.LEADER_APPROVE).target(ApproveStates.FAIL).event(ApproveEvent.LEADER_APPROVE_FAIL);
    }

    @Bean
    public StateMachineListener<ApproveStates, ApproveEvent> listener() {
        return new StateMachineListenerAdapter<ApproveStates, ApproveEvent>() {
            @Override
            public void stateChanged(State<ApproveStates, ApproveEvent> from, State<ApproveStates, ApproveEvent> to) {
                log.info("State change to: {}", to.getId());
            }

            @Override
            public void stateMachineError(StateMachine<ApproveStates, ApproveEvent> stateMachine, Exception exception) {
                log.error("Exception caught: {}", exception.getMessage(), exception);
            }

            @Override
            public void eventNotAccepted(Message<ApproveEvent> event) {
                ApproveTask task = (ApproveTask) event.getHeaders().get("task");
                log.error("Order state machine can't change state {} --> {}", Objects.requireNonNull(task).getApproveStates(), event.getPayload());
            }
        };
    }

    public StateMachinePersist<ApproveStates, ApproveEvent, String> persist() {
        return new StateMachinePersist<ApproveStates, ApproveEvent, String>() {
            private final Map<String, StateMachineContext<ApproveStates, ApproveEvent>> contexts = new HashMap<>();
            @Override
            public void write(StateMachineContext<ApproveStates, ApproveEvent> context, String contextObj) throws Exception {
                contexts.put(contextObj, context);
            }

            @Override
            public StateMachineContext<ApproveStates, ApproveEvent> read(String contextObj) throws Exception {
                return contexts.get(contextObj);
            }
        };
    }
}
