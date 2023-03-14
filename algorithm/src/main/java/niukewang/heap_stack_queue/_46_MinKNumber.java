package niukewang.heap_stack_queue;

import niukewang.binary_tree.TreeNode;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class _46_MinKNumber {

    public static void main(String[] args) {
        int[] arr = {4, 5, 1, 6, 2, 7, 3, 8};
        System.out.println(GetLeastNumbers_Solution(arr, 3));
    }

    public static ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        ArrayList<Integer> res = new ArrayList<>();
        int length = input.length;
        if (k == 0 || input == null || input.length < k) {
            return res;
        }
        for (int i = 0; i < k; i++) {
            // 按需要建伪堆数组长度,建立小顶堆
            minHeap(input, length - i);
            // 根节点值最小，加入结果列表
            res.add(input[0]);
            // 将第一个节点和最后一个节点交换, 并且需要建伪堆的数组长度减 1
            input[0] ^= input[length - i - 1];
            input[length -i -1] = input[0] ^ input[length - i - 1];
            input[0] ^= input[length - i - 1];
        }

        return res;
    }

    /**
     * 构建小顶堆(伪)
     * 将数组理解成完全二叉树, 构建类似于小顶堆
     * 从最后一个非叶子节点开始往前遍历
     * 如果该节点值大于子节点，则交换
     * 最终根节点一定是最小的值
     * @param input
     * @param length
     */
    public static void minHeap(int input[], int length){
        // 获取最后一个非叶子节点
        int nonLeafIndex = length / 2 - 1;
        for (int i = nonLeafIndex; i >= 0; i--) {
            int left = 2 * (i + 1) - 1;
            // 大于左节点则交换
            if (input[i] > input[left]) {
                input[i] ^= input[left];
                input[left] = input[i] ^ input[left];
                input[i] ^= input[left];
            }
            int right = 2 * (i + 1);
            // 大于右节点则交换
            if (right < length && input[i] > input[right]) {
                input[i] ^= input[right];
                input[right] = input[i] ^ input[right];
                input[i] ^= input[right];
            }
        }
    }

    public ArrayList<Integer> GetLeastNumbers_SolutionByQueue(int [] input, int k) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        //排除特殊情况
        if(k == 0 || input.length == 0)
            return res;
        //大根堆
        PriorityQueue<Integer> q = new PriorityQueue<>((o1, o2)->o2.compareTo(o1));
        //构建一个k个大小的堆
        for(int i = 0; i < k; i++)
            q.offer(input[i]);
        for(int i = k; i < input.length; i++){
            //较小元素入堆
            if(q.peek() > input[i]){
                q.poll();
                q.offer(input[i]);
            }
        }
        //堆中元素取出入数组
        for(int i = 0; i < k; i++)
            res.add(q.poll());
        return res;
    }
}
