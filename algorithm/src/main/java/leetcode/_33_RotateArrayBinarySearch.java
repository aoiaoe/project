package leetcode;

/**
 * 旋转数组二分搜索
 */
public class _33_RotateArrayBinarySearch {

    public static void main(String[] args) {
        int[] arr = {1,3};
        System.out.println(search(arr, 3));
    }

    /**
     * 二分遍历查找
     *      数组经过2分之后,一定右移部分是有序的, 如果无序，则再进行二分
     * @param nums
     * @param target
     * @return
     */
    public static int searchBinarySearch(int[] nums, int target) {
        int len = nums.length;
        if(len == 1){
            return nums[0] == target ? 0 : -1;
        }
        int left = 0, right = len - 1;
        int mid;
        while (left < right){
            mid = (left + right) >> 1;
            if(nums[mid] == target){
                return mid;
            }
            if(nums[0] < nums[mid]){ // 代表有序, 正常二分
                if(nums[0] < target && target < nums[mid]){
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else { // 代表无序
                // TODO
            }
        }
        return -1;
    }
    /**
     * 普通遍历查找
     * @param nums
     * @param target
     * @return
     */
    public static int search(int[] nums, int target) {
        int len = nums.length;
        if(nums[0] == target){
            return 0;
        } else if(nums[0] > target){
            if(nums[len - 1] == target){
                return len - 1;
            }
            for (int i = len - 2; i > 0 ; i--) {
                if(nums[i] == target){
                    return i;
                }
                if(nums[i - 1] > nums[i]){
                    break;
                }
            }
        } else {
            for (int i = 1; i < len ; i++) {
                if(nums[i] == target){
                    return i;
                }
                if(nums[i] < nums[i - 1]){
                    break;
                }
            }
        }
        return -1;
    }
}
