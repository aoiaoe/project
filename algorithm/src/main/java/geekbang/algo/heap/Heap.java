package geekbang.algo.heap;


/**
 * @author jzm
 * @date 2023/4/13 : 09:47
 */
public class Heap {

    public int length;
    public int cap;
    public int[] v;
    public boolean smallTop; // 是否小顶堆

    public Heap(int count) {
        v = new int[count];
        length = count;
        cap = 0;
        smallTop = true;
    }

    public Heap(int count, boolean smallTop) {
        this(count);
        this.smallTop = smallTop;
    }

    public Heap(int[] data) {
        v = data;
        length = data.length;
        cap = length;
        smallTop = true;
    }

    public Heap(int[] data, boolean smallTop) {
        this(data);
        this.smallTop = smallTop;
    }

    public void insert(int val) {
        // 堆满
        if (cap == length) {
            if (smallTop) {
                // 小顶堆，
            }
        }
        v[cap++] = val;
        // 堆化
        heapify(cap - 1, true);
    }

    private void heapify(int index, boolean up) {
        // 小顶堆
        if (smallTop) {
            if (up) {
                while (index > 0 && v[(index - 1) / 2] > v[index]) {
                    swap((index - 1) / 2, index);
                    index = (index - 1) / 2;
                }
            } else {
                while(index < cap){
                    int minPos; // 记录左右子节点中较小的那一个
                    // 无左边子节点，则是叶子节点，直接退出
                    if((index * 2) + 1 >= cap){
                        break;
                    }
                    // 暂时记录左边子节点最小
                    minPos = (index * 2) + 1;
                    // 如果存在右边子节点
                    // 并且右边子节点比左边子节点更小，则右边子节点最小
                    if((index + 1) * 2 < cap && v[minPos] > v[(index + 1) * 2]){
                        minPos = (index + 1) * 2;
                    }
                    // 如果不大于最小的子节点，退出
                    if(v[index] < v[minPos]){
                        break;
                    }
                    // 大于子节点，则与子节点中较小的交换位置
                    swap(index, minPos);
                    index = minPos;
                }
            }
        } else {
            if(up) {
                while (index > 0 && v[(index - 1) / 2] < v[index]) {
                    swap((index - 1) / 2, index);
                    index = (index - 1) / 2;
                }
            } else {
                while(index < cap){
                    int maxPos; // 记录左右子节点中较小的那一个
                    // 无左边子节点，则是叶子节点，直接退出
                    if((index * 2) + 1 >= cap){
                        break;
                    }
                    // 暂时记录左边子节点最大
                    maxPos = (index * 2) + 1;
                    // 如果存在右子节点，
                    // 并且左子节点小于右子节点，则记录为右边子节点最小
                    if((index + 1) * 2 < cap && v[maxPos] < v[(index + 1) * 2]){
                        maxPos = (index + 1) * 2;
                    }
                    // 如果不小于最大的子节点，退出
                    if(v[index] >= v[maxPos]){
                        break;
                    }
                    // 小于子节点，则与子节点中较小的交换位置
                    swap(index, maxPos);
                    index = maxPos;
                }
            }
        }
    }

    public int remove(int index) {
        if (index >= cap) {
            return -1;
        }
        int res = v[index];
        swap(index, --cap);
        v[cap] = 0;
        heapify(index, false);
        return res;
    }

    public void disp() {
        for (int i = 0; i < length; i++) {
            System.out.print("[" + i + "]: " + v[i] + " -> ");
        }
        System.out.println();
    }

    private void swap(int idx1, int idx2) {
        v[idx1] = v[idx1] ^ v[idx2];
        v[idx2] = v[idx1] ^ v[idx2];
        v[idx1] = v[idx1] ^ v[idx2];
    }


}
