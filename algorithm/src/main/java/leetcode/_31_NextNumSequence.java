package leetcode;

import java.util.Arrays;

/**
 * 整数数组的一个 排列  就是将其所有成员以序列或线性顺序排列。
 * 例如，arr = [1,2,3] ，以下这些都可以视作 arr 的排列：[1,2,3]、[1,3,2]、[3,1,2]、[2,3,1] 。
 *
 * 即 一个数组组成一个数字，它的下一个序列为： 比当前组成的数字刚好大一点的数字序列
 * 如: arr = {1,2,3,4,6,5}, 它的下一个序列 = {1,2,3,5,6,4}
 * 如果arr组成的数组已经为最大，则转换为最小
 * 如: arr = {3,2,1}, 下一个序列 = {1,2,3}
 */
public class _31_NextNumSequence {

    public static void main(String[] args) {
//        int[] arr = {1,2,3,4,6,5};
        int[] arr = {2,3,1};
        nextPermutation(arr);
        System.out.println(Arrays.toString(arr));

    }

    /**
     * 步骤:
     *      1、从后往前遍历： 找到第一队升序对，记下标为i, 则arr[i] -> arr[end]都为降序
     *      2、再次后往前遍历：找到第一个大于arr[i]的下标, 记为 j
     *      3、交换arr[i] 和 arr[j]
     *      4、将i后的所有数字冲排序为升序，即为下一个序列
     *
     *      5、特殊情况: 如果第一步找不到一个i，则代表为降序数组，直接执行步骤4
     * @param nums
     */
    public static void nextPermutation(int[] nums) {
        int len;
        if(nums == null ||  (len = nums.length) == 1){
            return;
        }
        int i = len - 2;
        // 从后向前遍历，找到第一个升序对
        for (; i >= 0 ; i--) {
            if(nums[i] < nums[i + 1]){
                break;
            }
        }
        // 如果找到了升序对
        if(i != -1){
            // 则从后向前寻找第一个大于arr[i]的数字
            for (int j = len - 1; j > i ; j--) {
                if(nums[j] > nums[i]){
                    swap(nums, j, i);
                    break;
                }
            }
        }
        // 从i + 1开始将降序改为升序(包含特殊请款全是降序的序列: i = -1)
        int left = i + 1, right = len - 1;
        while (left < right){
            swap(nums, left++, right--);
        }
    }

    public static void swap(int[] nums, int i, int j){
        nums[j] = nums[j] ^ nums[i];
        nums[i] = nums[j] ^ nums[i];
        nums[j] = nums[j] ^ nums[i];
    }

}
