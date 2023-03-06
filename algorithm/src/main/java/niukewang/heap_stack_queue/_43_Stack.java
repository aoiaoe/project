package niukewang.heap_stack_queue;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class _43_Stack {

    public static void main(String[] args) {
        _43_Stack ins = new _43_Stack();
        ins.push(9);
        ins.push(1);
        ins.push(6);
        System.out.println(ins.min());
        ins.pop();
        ins.pop();
        System.out.println(ins.min());
    }

    // 双栈实现
    Stack<Integer> stack1 = new Stack<>();
    // 保存最小值的栈
    Stack<Integer> minStack = new Stack<>();

    /**
     * 思路:
     *      0、双栈： 保存数据的正常栈, 保存最小数的最小数栈
     *      1、当一个数入栈, 需要先入正常栈
     *      2、如果小于最小数栈顶元素，则将数据也写入最小数栈顶
     *      3、如果大于最小数栈顶元素, 则最小数栈顶元素复制一份
     *      4、出栈的时候，两个栈同时出栈栈顶元素，这样就可以保证最小数栈栈顶元素，总是最小值
     *      例如: s1:正常栈  minS:最小数栈
     *          1入栈， s1: 1   minS: 1                获取最小值： 1
     *          2入栈： s1: 1,2  minS: 1, 1            获取最小值： 1    (因为2不小于minS栈顶，则复制minS栈顶元素入minS栈)
     *          -1入栈： s1: 1,2,-1  minS: 1,1,-1      获取最小值: -1
     *          同时出栈： s1: 1,2  minS: 1,1            获取最小值: -1
     * @param node
     */
    public void push(int node) {
        // 入正常栈
        stack1.push(node);
        // 如果最小数栈为空或者小于最小数栈顶，则入最小数栈
        if (minStack.empty() || minStack.peek() > node) {
            minStack.push(node);
        } else {
            // 否则，则复制栈顶的最小值入最小值栈
            minStack.push(minStack.peek());
        }
    }

    public void pop() {
        stack1.pop();
        minStack.pop();
    }

    public int top() {
        return stack1.peek();
    }

    public int min() {
        return minStack.peek();
    }

    // 自我双栈实现
//    int min = Integer.MAX_VALUE;
//    Stack<Integer> stack1 = new Stack<>();
//    Stack<Integer> stack2 = new Stack<>();
//
//    public void push(int node) {
//        stack1.push(node);
//        if(node < min){
//            min = node;
//        }
//    }
//
//    public void pop() {
//        int res = stack1.pop();
//        // 重新获取最小值和栈顶元素
//        int node;
//        int tmpMin = Integer.MAX_VALUE;
//        while (!stack1.empty()){
//            node = stack1.pop();
//            stack2.push(node);
//            if(node < tmpMin){
//                tmpMin = node;
//            }
//        }
//        while (!stack2.empty()){
//            stack1.push(stack2.pop());
//        }
//        min = tmpMin;
//    }
//
//    public int top() {
//        return stack1.peek();
//    }
//
//    public int min(){
//        return min;
//    }

//    // 队列实现
//    LinkedList<Integer> stack = new LinkedList<>();
//    int min = Integer.MAX_VALUE;
//    public void push(int node) {
//        stack.add(node);
//        if(node < min){
//            min = node;
//        }
//    }
//
//    public void pop() {
//        int res = stack.removeLast();
//        // 如果等于最小值, 则重新获取最小值
//        if(res == min){
//            min = Integer.MAX_VALUE;
//            for (Integer value : stack) {
//                if(value < min){
//                    min = value;
//                }
//            }
//        }
//    }
//
//    public int top() {
//        return stack.getLast();
//    }
//
//    public int min() {
//        return min;
//    }
}
