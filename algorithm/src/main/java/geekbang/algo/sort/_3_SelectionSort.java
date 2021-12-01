package geekbang.algo.sort;

import java.util.Arrays;

/**
 * 选择排序: 类似于插入排序，也是将数组分为已排序和未排序两部分， 前面一部分为已排序部分，
 *          区别是，将已排序部分的下一个索引中的值 和 未排序部分中的对应数(最大/最小)进行交换形成新的已排序部分
 *
 * 涉及到数据的寻址和移动
 *
 * 原地排序  非稳定排序 时间复杂度O(n²)
 * @author jzm
 * @date 2021/11/30 : 2:40 下午
 */
public class _3_SelectionSort {

    public static void main(String[] args) {
        int[] array = ArrayCreator.createArray(10, 150);
        System.out.println(Arrays.toString(array));
        selectionSort(array, array.length);
        System.out.println(Arrays.toString(array));
    }

    public static void selectionSort(int arr[], int n){
        int index = -1;
        int tmp = 0;
        for (int i = 0; i < n - 1; i++) {
            index = i;
            for (int j = i + 1; j < n; j++) {
                if(arr[j] < arr[index]){
                    index = j;
                }
            }
            if(index != i){
                tmp = arr[i];
                arr[i] = arr[index];
                arr[index] = tmp;
            }
            System.out.println((i + 1) + " -> " + Arrays.toString(arr));
        }
    }
}
