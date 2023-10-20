package leetcode;

import java.util.*;

/**
 * 组合总和
 * 给你一个 无重复元素 的整数数组 candidates 和一个目标整数 target ，找出 candidates 中可以使数字和为目标数 target 的 所有 不同组合 ，
 * 并以列表形式返回。你可以按 任意顺序 返回这些组合。
 * candidates 中的 同一个 数字可以 无限制重复被选取 。如果至少一个数字的被选数量不同，则两种组合是不同的。
 * 对于给定的输入，保证和为 target 的不同组合数少于 150 个。
 *
 * 1 <= candidates.length <= 30
 * 2 <= candidates[i] <= 40
 * candidates 的所有元素 互不相同
 * 1 <= target <= 40
 */
public class _39_CombinationSum {

    public static void main(String[] args) {
        int arr[] = {2,3,6,7};
        List<List<Integer>> lists = combinationSum(arr, 6);
        for (List<Integer> list : lists) {
            System.out.println(list);
        }
    }

    static List<List<Integer>> res = new ArrayList<>();
    static Deque<Integer> path = new LinkedList<>();

    /**
     * 回溯算法 +剪枝
     *
     * 剪枝采用, 后续查找，不遍历前面已经查找过的下标
     * 如: [2,3,5,6,7], 查找target7
     *      第一次循环，遍历数字2, 得出结果 [2,2,2][2,5]
     *      后续遍历,就不需要使用数字2,因为数字2,已经把所有包含数字2的情况枚举完了
     * @param candidates
     * @param target
     * @return
     */
    public  static List<List<Integer>> combinationSum(int[] candidates, int target) {
        int len = candidates.length;

        if(len < 1){
            return res;
        }
        combinationSum(candidates, 0, target);
        return res;
    }

    public static void combinationSum(int[] candidates, int begin, int target){
        if(target < 0){
            return;
        }
        if(target == 0){
            res.add(new ArrayList<>(path));
            return;
        }
        // 剪枝, 增加每次最外层遍历的下标, 即可以跳过已经遍历过的数字，完成去重
        for (int i = begin; i < candidates.length; i++) {
            path.push(candidates[i]);
            combinationSum(candidates, i, target - candidates[i]);
            path.pop();
        }

    }

}
