package geekbang.algo.sort;

/**
 * 快速排序
 * @author jzm
 * @date 2021/12/2 : 5:42 下午
 */
public class _5_QuickSort {

    public static void main(String[] args) {
        int[] array = ArrayCreator.createArray(1000, 30000);
        ArrayCreator.display(array);
        quickSort(array, 0, array.length - 1);
        ArrayCreator.isSorted(array);
        ArrayCreator.display(array);
    }

    /**
     * 递归进行快速排序
     * 分治法
     * 选择一个节点pivot，每次子任务都将小于pivot（小组） 和 大于pivot（大组）的分为两组
     * 再分别对两组进行快排
     * 直至分组不可再分
     * @param arr
     * @param start
     * @param end
     */
    public static void quickSort(int arr[], int start, int end) {
        if (start >= end)
            return;

        int pivot = partition(arr, start, end);
        quickSort(arr, start, pivot -1);
        quickSort(arr, pivot + 1, end);

    }

    /**
     * 原地排序分组
     * @param arr
     * @param start
     * @param end
     * @return
     */
    private static int partition(int[] arr, int start, int end) {
        // 选择最后一个节点当做pivot
        int pivot = arr[end];
        // 标记小组的下标， 为实现原地排序
        // 最后[start, i)就是小组， i就是pivot，(i, end]就是大组
        int i = start;
        int temp;
        // 除了自身，每个节点都与pivot比较
        // 如果 < pivot，则将当前节点与 i 代表的节点交换
        for (int j = start; j <= end - 1; j++) {
            if(arr[j] < pivot){
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
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

}
