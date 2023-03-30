package interview;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 三数之和等于0的不重复元祖
 */
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new LinkedList<>();
        if (nums == null || nums.length < 3) { //为空或者元素个数小于 3，直接返回
            return result;
        }
        Arrays.sort(nums); //排序
        for (int i = 0; i < nums.length - 2; i++) { //遍历到倒数第三个，因为是三个数总和
            if (nums[i] > 0) { //大于 0 可以直接跳出循环了
                break;
            }
            if (i > 0 && nums[i] == nums[i - 1]) { //过滤重复
                continue;
            }
            int left = i + 1; //左指针
            int right = nums.length - 1; //右指针
            int target = -nums[i]; //目标总和，是第 i 个的取反，也就是 a+b+c=0,则 b+c=-a 即可
            while (left < right) {
                if (nums[left] + nums[right] == target) { //b+c=-a,满足 a+b+c=0
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    left++; //左指针右移
                    right--; //右指针左移
                    while (left < right && nums[left] == nums[left - 1])
                        left++; //继续左边过滤重复
                    while (left < right && nums[right] == nums[right + 1])
                        right--; //继续右边过滤重复
                } else if (nums[left] + nums[right] < target) {
                    left++; //小于目标值，需要右移，因为排好序是从小到大的
                } else {
                    right--;
                }
            }
        }
        return result;
    }
}