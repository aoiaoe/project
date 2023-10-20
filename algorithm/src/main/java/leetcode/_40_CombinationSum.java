package leetcode;

import java.util.*;

/**
 * 组合总和
 *
 * 给定一个候选人编号的集合 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
 * candidates 中的每个数字在每个组合中只能使用 一次 。
 * 注意：解集不能包含重复的组合。
 *
 * 1 <= candidates.length <= 30
 * 2 <= candidates[i] <= 40
 * candidates 的所有元素 互不相同
 * 1 <= target <= 40
 */
public class _40_CombinationSum {

    public static void main(String[] args) {
        int arr[] = {1,2,3,2,1, 3,4,5,6,7};
        List<List<Integer>> lists = combinationSum(arr, 6);
        for (List<Integer> list : lists) {
            System.out.println(list);
        }
    }

    static List<List<Integer>> res = new ArrayList<>(100);
    static Deque<Integer> path = new LinkedList<>();

    /**
     * 回溯算法 +剪枝
     * @param candidates
     * @param target
     * @return
     */
    public  static List<List<Integer>> combinationSum(int[] candidates, int target) {
        int len = candidates.length;

        if(len < 1){
            return res;
        }
        // 先排序
        Arrays.sort(candidates);
        combinationSum(candidates, 0, target, true);
        return res;
    }

    public static void combinationSum(int[] candidates, int begin, int target, boolean out){
        if(target < 0){
            return;
        }
        if(target == 0){
            res.add(new ArrayList<>(path));
            return;
        }
        // 记录当前层数中哪些数字用过
        // 剪枝, 增加每次最外层遍历的下标, 即可以跳过已经遍历过的数字，完成去重
        for (int i = begin; i < candidates.length; i++) {
            // 剪枝, 因为已经升序排列，后面肯定为负
            if(target < 0){
                break;
            }
            // 因为已经排过序
            // 剪枝, 增加每次最外层遍历的下标, 即可以跳过已经遍历过的数字，完成去重
            if(i > begin && candidates[i] == candidates[i - 1]) {
                continue;
            }
            path.push(candidates[i]);
            // 因为数字不能重复使用，所以从当下数字的下一个数字开始 begin = i + 1
            combinationSum(candidates, i + 1, target - candidates[i], false);
            path.pop();
        }
    }

}
