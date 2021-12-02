package geekbang.algo.sort;

/**
 * 归并排序：
 *  利用分治思想，将数组划分为若干小数组，先对若干小数组进行排序， 最后依次将小数组进行归并，达到最后的全部有序
 * @author jzm
 * @date 2021/12/2 : 10:44 上午
 */
public class _4_MergeSort {

    public static void main(String[] args) {
        int[] array = ArrayCreator.createArray(1000, 1000000);

        ArrayCreator.display(array);
        mergeSort(array,0, array.length - 1);
        ArrayCreator.display(array);

    }

    /**
     * 排序：
     *  递归
     * @param arr       数字
     * @param start     起始索引
     * @param end       终止索引
     */
    public static void mergeSort(int[] arr, int start, int end){
        if(start >= end) return;
        int middle = (start + end) / 2;

        mergeSort(arr, start, middle);
        mergeSort(arr, middle + 1, end);

        merge(arr, start, middle, middle + 1, end);
    }

    /**
     * 归并
     * @param arr  
     */
    public static void merge(int[] arr, int start1, int end1,
                              int start2, int end2){
        int[] tmp = new int[end2-start1+1];
        int index = 0;
        int start = start1;
        while(start1 <= end1 || start2 <= end2){
            if(arr[start1] <= arr[start2]){
                tmp[index++] = arr[start1++];
            } else {
                tmp[index++] = arr[start2++];
            }

            // 判断是否有数组已经到末尾了，如果有 将另一个数组全部数据全部加到临时数组
            if(start1 > end1){
                for (int i = start2; i <= end2; i++) {
                    tmp[index++] = arr[i];
                }
                break;
            }
            if(start2 > end2){
                for (int i = start1; i <= end1; i++) {
                    tmp[index++] = arr[i];
                }
                break;
            }
        }
        index = 0;
        // 将排好序的临时数组拷贝回原数组覆盖
        for (int i = start; i <= end2; i++) {
            arr[i] = tmp[index++];
        }
    }
}
