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
        int[] array = ArrayCreator.createArray(1000000, 100000000);
//        System.out.println(Arrays.toString(array));
        System.out.println("是否有序：" + SortTest.testInSort(array));
        insertionSortV3(array);
        System.out.println("插入排序之后是否有序： " + SortTest.testInSort(array));
//        System.out.println(Arrays.toString(array));
    }

    /**
     * 升序插入排序
     * 将数据分为已排序和未排序两部分
     * 依次从未排序中选择一个数据(假设数据A)，插入到已排序部分中
     * 如何找到这个未排序数据的位置呢：
     *  从已排序部分从后向前比较，如果值大于A，则将数据向后移动一位，直到找到不大于A的数据，则是A插入的位置
     * @param arr
     */
    public static void insertionSortV3(int arr[]){
        int length = arr.length;
        int val;
        int j;
        for (int i = 1; i < length; i++) {
            val = arr[i]; // 记录当前排序的值
            j = i;
            // 从当前排序值索引向前寻找比较
            // 因为是升序，所以将所有大于当前排序值的数据向后移动一位
            for (; j > 0; j--) {
                if(arr[j-1] > val){
                    arr[j] = arr[j-1];
                } else
                    // 如果不大于，则跳出内循环，此处为当前排序值应该插入的位置
                    break;
            }
            arr[j] = val;
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
