package niukewang.search_sort;

/**
 * 有一个长度为 n 的非降序数组，比如[1,2,3,4,5]，将它进行旋转，即把一个数组最开始的若干个元素搬到数组的末尾，
 * 变成一个旋转数组，比如变成了[3,4,5,1,2]，或者[4,5,1,2,3]这样的。请问，给定这样一个旋转数组，求数组中的最小值。
 */
public class _21_MinNumberInRotateArray {

    public static void main(String[] args) {
        int[] arr = {3,4,5,1,2};
        arr = new int[]{3,100,200,3};
        System.out.println(minNumberInRotateArray(arr));
    }

    /**
     * 二分查找 + 有序特性
     * 因为数组最开始有序,经过旋转过后，最小值一定在后面
     * @param array
     * @return
     */
    public static int minNumberInRotateArray(int [] array) {
        int left = 0;
        int right = array.length - 1;
        while(left < right){
            // 找到中点
            int mid = (left + right) / 2;
            // 最小的数字在mid右边
            if(array[mid] > array[right])
                left = mid + 1;
                //无法判断，一个一个试
            else if(array[mid] == array[right])
                right--;
                //最小数字要么是mid要么在mid左边
            else
                right = mid;
        }
        return array[left];
    }

    /**
     * 普通二分查找
     * @param array
     * @return
     */
    public static int minNumberInRotateArrayByBinarySearch(int [] array) {
        if(array.length == 1){
           return array[0];
        } else if (array.length == 2){
            return array[0] > array[1] ? array[1] : array[0];
        }

        return binarySearchMin(array, 0, array.length - 1);
    }

    public static int binarySearchMin(int[] arr, int start, int end){
        if(start >= end){
            return arr[start];
        }
        int mid = (start + end) / 2;
        int left = binarySearchMin(arr, start, mid);
        int right = binarySearchMin(arr, mid + 1, end);
        return left < right ? left : right;
    }
}
