package niukewang.heap_stack_queue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 给定一个长度为 n 的数组 num 和滑动窗口的大小 size ，找出所有滑动窗口里数值的最大值。
 * 例如，如果输入数组{2,3,4,2,6,2,5,1}及滑动窗口的大小3，那么一共存在6个滑动窗口，他们的最大值分别为{4,4,6,6,6,5}；
 * 针对数组{2,3,4,2,6,2,5,1}的滑动窗口有以下6个： {[2,3,4],2,6,2,5,1}， {2,[3,4,2],6,2,5,1}，
 * {2,3,[4,2,6],2,5,1}， {2,3,4,[2,6,2],5,1}， {2,3,4,2,[6,2,5],1}， {2,3,4,2,6,[2,5,1]}。
 *
 * 窗口大于数组长度或窗口长度为0的时候，返回空。
 */
public class _45_MaxInWindow {

    public static void main(String[] args) {
        int[] arr = {2,3,4,2,6,2,5,1};
        System.out.println(maxInWindows(arr, 3));
    }

    public static ArrayList<Integer> maxInWindows(int [] num, int size) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        //窗口大于数组长度的时候，返回空
        if(size <= num.length && size != 0){
            //双向队列
            ArrayDeque<Integer> dq = new ArrayDeque<Integer>();
            //先遍历一个窗口
            for(int i = 0; i < size; i++){
                //去掉比自己先进队列的小于自己的值
                while(!dq.isEmpty() && num[dq.peekLast()] < num[i])
                    dq.pollLast();
                dq.add(i);
            }
            //遍历后续数组元素
            for(int i = size; i < num.length; i++){
                //取窗口内的最大值
                res.add(num[dq.peekFirst()]);
                // dq.peekFirst() 取出队列头的数组索引
                // 确保队列头的索引刚好等于i - size, 这样窗口中的值才等于size
                // 因为队列头是窗口start, i是窗口end
                // 就是保证窗口容量正确
                while(!dq.isEmpty() && dq.peekFirst() <= (i - size))
                    //弹出窗口移走后的值
                    dq.pollFirst();
                //加入新的值前，去掉比自己先进队列的小于自己的值
                while(!dq.isEmpty() && num[dq.peekLast()] < num[i])
                    dq.pollLast();
                dq.add(i);
            }
            res.add(num[dq.pollFirst()]);
        }
        return res;
    }

}
