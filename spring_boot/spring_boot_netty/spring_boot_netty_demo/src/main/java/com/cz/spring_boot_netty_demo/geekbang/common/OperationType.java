package com.cz.spring_boot_netty_demo.geekbang.common;

import com.cz.spring_boot_netty_demo.geekbang.common.auth.AuthOperation;
import com.cz.spring_boot_netty_demo.geekbang.common.auth.AuthOperationResult;
import com.cz.spring_boot_netty_demo.geekbang.common.keepalive.KeepAliveOperation;
import com.cz.spring_boot_netty_demo.geekbang.common.keepalive.KeepAliveOperationResult;
import com.cz.spring_boot_netty_demo.geekbang.common.order.OrderOperation;
import com.cz.spring_boot_netty_demo.geekbang.common.order.OrderOperationResult;
import lombok.Getter;

import java.util.function.Predicate;
@Getter
public enum OperationType {

    AUTH(1, AuthOperation.class, AuthOperationResult.class),
    KEEPALIVE(2, KeepAliveOperation.class, KeepAliveOperationResult.class),
    ORDER(3, OrderOperation.class, OrderOperationResult.class);

    private int opCode;
    private Class<? extends Operation> operationClass;
    private Class<? extends OperationResult> operationResultClass;

    OperationType(int opCode, Class<? extends Operation> operationClass,Class<? extends OperationResult> operationResultClass){
        this.opCode = opCode;
        this.operationClass = operationClass;
        this.operationResultClass = operationResultClass;
    }

    public static OperationType fromOpCode(int type){
        return getOperationType(requestType -> requestType.opCode == type);
    }

    public static OperationType fromOperation(Operation operation){
        return getOperationType(requestType -> requestType.operationClass == operation.getClass());
    }

    private static OperationType getOperationType(Predicate<OperationType> predicate){
        OperationType[] values = values();
        for (OperationType operationType : values) {
            if(predicate.test(operationType)){
                return operationType;
            }
        }

        throw new AssertionError("no found type");
    }

}
