package com.cz.sort;

/**
 * @author alian
 * @date 2020/8/27 下午 5:35
 * @since JDK8
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = {3,7,4,2,6,8,1,9,10,5};
        quickSort(arr, 0, arr.length-1);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

    public static void quickSort(int arr[], int left, int right){
        if(left > right){
            return;
        }
        int i = left;
        int j = right;
        int base = arr[i];
        int temp;
        while (i < j){
            while (arr[i] <= base && i < j){
                i++;
            }
            while (arr[j] >= base && j > i){
                j--;
            }

            if(i < j){
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        arr[left] = arr[i];
        arr[i] = base;

        quickSort(arr, left, j -1);
        quickSort(arr, j+1, right);
    }
}
