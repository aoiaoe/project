package com.cz.inner_class;

import lombok.Data;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 内部类使用外部类方法
 * 测试来源场景：
 *  AbstractQueuedSynchronizer + 内部类: ConditionObject
 * @author jzm
 * @date 2023/3/23 : 17:40
 */
@Data
public abstract class OutterClass {

    private int state;


    public class InnerClass{
        public InnerClass(){}
        public void printOutterState(){
            System.out.println(getState());
        }
    }

    abstract InnerClass getInnerClassInstance();

    public static void main(String[] args) {
        OutterClass outterClass = new OutterClass(){
            @Override
            InnerClass getInnerClassInstance() {
                return new InnerClass();
            }
        };
        InnerClass innerClass = outterClass.getInnerClassInstance();

        innerClass.printOutterState();

        outterClass.setState(101);

        innerClass.printOutterState();

    }
}
