package geekbang.algo.sort;

import org.junit.jupiter.api.Test;


/**
 * 三个原地排序的算法性能比较
 *
 * @author jzm
 * @date 2021/12/1 : 2:07 下午
 */
public class SortTest {

    int arr[] = null;
    int count = 100000;
    int length = 1000;

    /**
     * 冒泡算法循环100000次, 数组长度:1000,所用时间：320
     * 冒泡算法循环100000次, 数组长度:1000,所用时间：48856
     * 优化版冒泡算法循环100000次, 数组长度:1000,所用时间：39314
     */
    @Test
    public void bubble() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            arr = createArray();
            _1_BubbleSort.bubbleSortOptimization(arr);
        }
        long end = System.currentTimeMillis();
        System.out.println("冒泡算法循环" + count + "次, 数组长度:" + length + ",所用时间：" + (end - start));
    }

    /**
     * 插入算法循环100000次, 数组长度:100,所用时间：140
     * 插入算法循环100000次, 数组长度:1000,所用时间：10050
     *
     */
    @Test
    public void insert() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            arr = createArray();
            _2_InsertionSort.insertionSortV1(arr);
        }
        long end = System.currentTimeMillis();
        System.out.println("插入算法循环" + count + "次, 数组长度:" + length + ",所用时间：" + (end - start));
    }

    public int[] createArray(){
//        return ArrayCreator.createArray(length, 10000);
        return ArrayCreator._1_00();
//        return ArrayCreator._1_000();
    }
}
