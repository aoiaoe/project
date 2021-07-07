package com.cz.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * 快排,有bug，待修复
 *
 * @author alian
 * @date 2020/8/27 下午 5:35
 * @since JDK8
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = new int[10];
        createArrValues(arr);
        System.out.println(Arrays.toString(arr));
        quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));

//        quickSort(arr, arr.length);

    }

    public static void quickSort(int arr[], int left, int right) {
        if (left >= right) {
            return;
        }
        int i = left;
        int j = right;
        int base = arr[i];
        int temp;
        // 0,1,2,3,4,5,6,7,8, 9    i  j
        // 3,7,4,2,6,8,1,9,10,5    1  6
        // 3,1,4,2,6,8,7,9,10,5    2  3
        // 3,1,2,4,6,8,7,9,10,5    3  3
        while (i < j) {
            while (arr[i] <= base && i < j) {
                i++;
            }
            while (arr[j] >= base && j > i) {
                j--;
            }

            if (i < j) {
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }


        quickSort(arr, left, j - 1);
        quickSort(arr, j + 1, right);
        // i 1
        // j 7
        // 3,1,4,2,6,8,7,9,10,5
    }

    public static void createArrValues(int[] arr) {
        final Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(20);
        }
    }

    /**
     * 快速排序
     */
    public static void quickSort(int[] arr, int len) {
        __quickSort(arr, 0, len - 1);
    }

    // 注意边界条件
    private static void __quickSort(int[] arr, int begin, int end) {
        if (begin >= end) {
            return;
        }

        // 一定要是 p-1！
        int p = partition(arr, begin, end); // 先进行大致排序，并获取区分点
        __quickSort(arr, begin, p - 1);
        __quickSort(arr, p + 1, end);
    }

    private static int partition(int[] arr, int begin, int end) {
        int pValue = arr[end];

        // 整两个指针，两个指针都从头开始
        // begin --- i-1（含 i-1）：小于 pValue 的区
        // i --- j-1（含 j-1）：大于 pValue 的区
        // j --- end：未排序区
        int i = begin;
        int j = begin;
        while (j <= end) {
            if (arr[j] <= pValue) {
                int temp = arr[j];
                arr[j] = arr[i];
                arr[i] = temp;
                i++;
                j++;
            } else {
                j++;
            }
        }

        return i - 1;
    }
}
