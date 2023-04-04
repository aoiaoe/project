package geekbang.algo.ssearch;

/**
 * 循环有序数组二分查找
 * @author jzm
 * @date 2023/3/29 : 13:12
 */
public class _10_CycleArrayBSearch {

    public static void main(String[] args) {
        int arr[] = {4,5,6,7,8,1,2,3};
//        arr = new int[]{0,1,2,3,4,5,6,7,8};
        for (int i = 1; i <= 8; i++) {
            System.out.println(search(arr, i));
        }
    }

    /**
     * 思路：
     *  将循环数组后面有序部分索引当做负数
     *          {4,5,6,7,8,1,2,3};
     *  真实索引  0,1,2,3,4,5,6,7
     *  逻辑索引  0,1,2,3,4,5,-3,-2,-1
     * @param arr
     * @param num
     * @return
     */
    public static int search(int arr[], int num){
        // 先找到最大节点的索引
        int length = arr.length;
        int max = arr[0];
        int end = length;
        for (int i = 1; i < length; i++) {
            if(arr[i] < max){
                end = i - 1;
                break;
            }
            max = arr[i];
            end = i;
        }
        // 如果索引为初始值，则说明非循环数组，则start为0
        int start = end == length ? 0 : end + 1 - length;
        int mid;
        int tmpIndex;
        while (start <= end){
            mid = (start + end) / 2;
            // 如果mid小于0，根据逻辑索引计算真实索引
            tmpIndex = mid < 0 ? length + mid : mid;
            if(arr[tmpIndex] == num){
                return tmpIndex;
            } else if(arr[tmpIndex] < num){
                start = mid + 1;
            } else {
                end = mid - 1;;
            }
        }
        return -1;
    }
}
