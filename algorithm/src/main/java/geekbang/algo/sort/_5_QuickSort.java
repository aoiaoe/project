package geekbang.algo.sort;

import java.util.Arrays;

/**
 * 快速排序
 * 原地排序
 * 不稳定
 * O(nlogn),极端情况为O(n²)
 * https://time.geekbang.org/column/article/41913
 *
 * @author jzm
 * @date 2021/12/2 : 5:42 下午
 */
public class _5_QuickSort {

    public static void main(String[] args) {
        int[] array = ArrayCreator.createArray(1000, 3000000);
//        int []array = {294, 42, 163, 3, 273, 138, 223, 21, 284, 248};
        ArrayCreator.display(array);
//        quickSort(array, 0, array.length - 1);
//        quickSortByHand(array, 0, array.length - 1);
        quickSort1(array, 0, array.length - 1);
        ArrayCreator.isSorted(array);
        ArrayCreator.display(array);
    }

    //--------------------手写-----------------------
    public static void quickSortByHand(int arr[], int start, int end) {
        if (start >= end) {
            return;
        }
        // 寻找指点
        int pivot = sortAndGetPivot(arr, start, end);
        // 再排序支点前面部分
        quickSortByHand(arr, start, pivot - 1);
        // 再排序支点后面部分
        quickSortByHand(arr, pivot + 1, end);
    }

    /**
     * 分区并找寻支点
     *
     * @param arr
     * @param start
     * @param end
     * @return
     */
    public static int sortAndGetPivot(int arr[], int start, int end) {
        // 将最后一个节点值当做支点
        int pivot = arr[end];
        // 记录待交换的节点索引
        int index = start;
        for (int i = start; i < end; i++) {
            // 如果当前值 < 支点值，则交换, 因为交换过了，所以将待交换的节点索引+1
            if (arr[i] < pivot) {
                if (index != i) {
                    arr[i] = arr[i] ^ arr[index];
                    arr[index] = arr[i] ^ arr[index];
                    arr[i] = arr[i] ^ arr[index];
                }
                index++;
            }
        }
        // index就是支点应该存在的索引
        // 将pivot与index交换
        if (index != end) {
            arr[index] = arr[end] ^ arr[index];
            arr[end] = arr[index] ^ arr[end];
            arr[index] = arr[index] ^ arr[end];
        }

        // 将支点所在的索引返回
        return index;
    }

    //-------------------------极客时间---------------

    /**
     * 递归进行快速排序
     * 分治法
     * 选择一个节点pivot，每次子任务都将小于pivot（小组） 和 大于pivot（大组）的分为两组
     * 再分别对两组进行快排
     * 直至分组不可再分
     *
     * @param arr
     * @param start
     * @param end
     */
    public static void quickSort(int arr[], int start, int end) {
        if (start >= end)
            return;

        int pivotIndex = partition(arr, start, end);
        quickSort(arr, start, pivotIndex - 1);
        quickSort(arr, pivotIndex + 1, end);

    }

    /**
     * 原地排序分组
     *
     * @param arr
     * @param start
     * @param end
     * @return
     */
    private static int partition(int[] arr, int start, int end) {
        // 选择最后一个节点当做pivot
        int pivot = arr[end];
        // 标记小组的下标， 为实现原地排序
        // 最后[start, i)就是小组， i就是pivot索引，(i, end]就是大组
        int i = start;
        int temp;
        // 除了自身，每个节点都与pivot比较
        // 如果 < pivot，则将当前节点与 i 代表的节点交换
        for (int j = start; j <= end - 1; j++) {
            if (arr[j] < pivot) {
                if (i != j) {
                    temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
                i++;
            }
        }
        // 最后将pivot和i节点交换
        // 因为有i++, 所以i代表的节点肯定是大组中的
        temp = arr[i];
        arr[i] = arr[end];
        arr[end] = temp;
        return i;
    }


    //---------------从微信公众号抄的算法------------------

    /**
     * 递归实现快排
     *
     * @param arr
     * @param low
     * @param high
     */
    public static void quickSort1(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partitionQS(arr, low, high);
            quickSort1(arr, low, pivotIndex - 1);
            quickSort1(arr, pivotIndex + 1, high);
        }
    }

    private static int partitionQS(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
