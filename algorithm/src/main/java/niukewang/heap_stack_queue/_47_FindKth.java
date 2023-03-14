package niukewang.heap_stack_queue;

import java.util.PriorityQueue;

public class _47_FindKth {

    /**
     * 小顶堆查找
     * @param a
     * @param n
     * @param K
     * @return
     */
    public int findKthByMinHeap(int[] a, int n, int K) {
        //小根堆
        PriorityQueue<Integer> q = new PriorityQueue<>((o1, o2)->o1.compareTo(o2));
        //构建一个k个大小的堆
        for(int i = 0; i < K; i++)
            q.offer(a[i]);
        for(int i = K; i < n; i++){
            //较小元素入堆
            if(q.peek() < a[i]){
                q.poll();
                q.offer(a[i]);
            }
        }
        //堆中元素取出入数组
        return q.peek();
    }
}
