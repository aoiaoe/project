package _1_two_sum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 的那 两个 整数，并返回它们的数组下标。
 * <p>
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/two-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class Solution {

    public static void main(String[] args) {
        int[] nums = new int[]{3, 2, 4};
        System.out.println(Arrays.toString(twoSum(nums, 6)));
    }

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        int sub = -1;
        Integer res = null;
        for (int i = 0; i < nums.length; i++) {
            sub = target - nums[i];
            if ((res = map.get(sub)) == null) {
                map.put(nums[i], i);
            } else {
                return new int[]{i, res};
            }
            res = null;
        }
        return null;
    }
}