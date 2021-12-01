package geekbang.algo.sort;

import java.util.Arrays;

/**
 * 插入排序:
 *  将数组分为已排序和未排序两部分， 前面一部分为已排序部分，
 *  每次从未排序中取一个数据，在已排序部分中向前比较，找到对应的位置插入数据形成新的已排序数据
 *
 *  涉及到数据的寻址和移动
 *
 *  原地排序  稳定排序   时间复杂度O(n²)
 */
public class _2_InsertionSort {

    public static void main(String[] args) {
        int[] array = ArrayCreator.createArray(10, 150);
        System.out.println(Arrays.toString(array));
        insertionSortV2(array);
        System.out.println(Arrays.toString(array));
    }

    /**
     * 插入排序v2：
     *  将寻址和数据移动放入一个循环
     * @param arr
     */
    public static void insertionSortV2(int arr[]){
        for (int i = 1; i < arr.length; i++) {
            int val = arr[i];
            int j = i - 1;
            for (; j >= 0 ; j--) {
                if(val < arr[j]){
                    arr[j + 1] = arr[j];
                } else
                    break;
            }
            arr[j + 1] = val;
            System.out.println(i + " -> " + Arrays.toString(arr));
        }
    }

    /**
     * 手写插入排序第一个版本
     *  缺点：将数据的寻址和移动分成了两步，略显复杂，空间也使用得更多
     * @param arr
     */
    public static void insertionSortV1(int arr[]){
        for (int i = 1; i < arr.length; i++) {
            int j = 0;
            for (; j < i; j++) {
                if(arr[i] < arr[j]){
                    break;
                }
            }
            int temp = arr[i];
            int index = i;
            int index2 = j;
            j++;
            while (j <= index){
                arr[index] = arr[index - 1];
                index--;
            }
            arr[index2] = temp;
            System.out.println(i + " ->> " + Arrays.toString(arr));
        }
    }


    /**
     * 极客时间
     * 插入排序，a表示数组，n表示数组大小
     * @param a
     * @param n
     */
    public void insertionSort(int[] a, int n) {
        if (n <= 1) return;

        for (int i = 1; i < n; ++i) {
            int value = a[i];
            int j = i - 1;
            // 查找插入的位置
            for (; j >= 0; --j) {
                if (a[j] > value) {
                    a[j+1] = a[j];  // 数据移动
                } else {
                    break;
                }
            }
            a[j+1] = value; // 插入数据
        }
    }
}
