package geekbang.algo.sort;

import java.util.Arrays;

/**
 * 冒泡排序：原地排序， 稳定排序， 时间复杂度O(n²)
 * 改进方法：增加标志位， 一轮循环之后，如果没有设计到交换，则认为有序退出
 */
public class _1_BubbleSort {

    public static void main(String[] args) {
//        int[] array = ArrayCreator.createArray(10, 15);
        int array[] = {14, 4, 1, 5, 0, 11, 9, 13, 16, 7};
        System.out.println(Arrays.toString(array));
        bubbleSort1(array);
//        bubbleSortOptimization(array);
//        System.out.println(Arrays.toString(array));
    }

    /**
     * 极客时间
     * @param a
     * @param n
     */
    // 冒泡排序，a表示数组，n表示数组大小
    public void bubbleSort(int[] a, int n) {
        if (n <= 1) return;
        for (int i = 0; i < n; ++i) {
            // 提前退出冒泡循环的标志位
            boolean flag = false;
            for (int j = 0; j < n - i - 1; ++j) {
                if (a[j] > a[j + 1]) {
                    // 交换
                    int tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                    flag = true; // 表示有数据交换
                }
            }
            if (!flag) break;
            // 没有数据交换，提前退出
        }
    }


    public static void bubbleSort(int arr[]) {
        int tmp = -1;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
//            System.out.println((i + 1) + " -> " + Arrays.toString(arr));
        }
    }

    /**
     * 一轮循环之后，如果不涉及数据交换，则有序退出
     *
     * @param arr
     */
    public static void bubbleSortOptimization(int arr[]) {
        int tmp = -1;
        Boolean flag = false;
        for (int i = 0; i < arr.length - 1; i++) {
            flag = false;
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                    flag = true;
                }
            }
//            System.out.println((i + 1) + " -> " + Arrays.toString(arr));
            if (!flag) {
                break;
            }
        }
    }


    public static void bubbleSort1(int[] arr){
        boolean flag = true;
        for (int i = 0; i < arr.length -1; i++) {
            flag = true;
            for (int j = 0; j < arr.length - i - 1; j++) {
                if(arr[j] > arr[j + 1]){
                    arr[j] = arr[j] ^ arr[j + 1];
                    arr[j + 1] = arr[j] ^ arr[j+ 1];
                    arr[j] = arr[j] ^ arr[j + 1];
                    flag = false;
                }
            }
            if(flag){
                break;
            }
            System.out.println(Arrays.toString(arr));
        }
    }
}
