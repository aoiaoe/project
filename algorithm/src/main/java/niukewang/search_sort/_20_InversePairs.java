package niukewang.search_sort;

/**
 * 查找数组中的逆序对
 * @author jzm
 * @date 2023/2/22 : 11:13
 */
public class _20_InversePairs {

    public int InversePairs(int [] array) {
        if(array == null || array.length < 1){
            return 0;
        }
        return 0;
    }

    public int MergeSort(int[] array, int start, int end){
        if(start >= end){
            return 0;
        }
        if((end - start) == 1 && array[end] < array[start]){
            return 1;
        }
        int mid = (start + end) / 2;
        return -1;
    }
}
