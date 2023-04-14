package geekbang.algo.heap;

/**
 * @author jzm
 * @date 2023/4/13 : 09:42
 */
public class HeapTest {

    public static void main(String[] args) {
        Heap heap = new Heap(10);
        heap.insert(1);
        heap.insert(3);
        heap.insert(4);
        heap.insert(6);
        heap.insert(5);
        heap.insert(9);
        heap.disp();
        heap.insert(7);
        heap.disp();
        heap.insert(8);
        heap.disp();
        heap.insert(2);
        heap.disp();
        heap.remove(0);
        heap.disp();
        heap.remove(0);
        heap.disp();
        heap.remove(0);
        heap.disp();
    }
}
