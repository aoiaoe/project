package geekbang.algo.dynamicprogramming;

import java.util.Arrays;

/**
 * @author jzm
 * @date 2023/4/11 : 17:48
 */
public class MinCoin {

    public static void main(String[] args) {
        int[] coins = {1, 3, 5};
        System.out.println("手写动态规划求最少硬币: " + minCoins(coins, 12));

        minCoins(coins, 12, 0);
        System.out.println("回溯求解最少硬币: " + MIN_COINS);


        System.out.println("动态规划求最少硬币: " + coinPlan2(coins, 12));
    }

    /**
     * 动态规划求解最少硬币
     *
     * @param coins  硬币数组，硬币需要价值从小到大排序
     * @param target
     * @return
     */
    public static int minCoins(int[] coins, int target) {
        // 初始化状态二维数组
        // 状态数组中索引代表金额，索引对应值代表当前金额需要的硬币数量
        int state[][] = new int[target + 1][target + 1];
        for (int[] ints : state) {
            Arrays.fill(ints, -1);
        }
        // 0块钱需要用0个硬币
        state[0][0] = 0;
        for (int i = 1; i <= target; i++) {
            for (int j = 0; j < target; j++) {
                state[i][j] = state[i-1][j];
            }
            for (int j = coins.length - 1; j >= 0; j--) {
                if (coins[j] <= i && state[i-1][i - coins[j]] >= 0) {
                    state[i][i] = state[i-1][i-coins[j]] + 1;
                    // 选择额度大的硬币解决问题之后，直接进行下一个金额的求解
                    // 因为更大的额度，所用的硬币个数一定更少
                    break;
                }
            }
        }
        System.out.println("状态数组");
        dispState(state);
        return state[target][target];
    }

    public static int MIN_COINS = Integer.MAX_VALUE;

    public static void minCoins(int[] coins, int target, int amount) {
        if (target < 0) {
            return;
        }
        if (target == 0) {
            if (amount < MIN_COINS) {
                MIN_COINS = amount;
            }
        }
        for (int i = 0; i < coins.length; i++) {
            minCoins(coins, target - coins[i], amount + 1);
        }
    }

    private static int coinPlan2(int[] coinTypes, int money) {
        int[] dp = new int[money + 1];
        Arrays.fill(dp, -1);
        dp[0] = 0;

        // 按照硬币面值排序
        Arrays.sort(coinTypes);
        // 复杂度O(M * N), 递推公式: f(n) = f(n - max(coinTypes)) + 1
        for (int i = 1; i <= money; i++) {
            // 从小问题开始解决，避免重复计算
            for (int j = coinTypes.length - 1; j >= 0; j--) {
                if (coinTypes[j] <= i && dp[i - coinTypes[j]] >= 0) {
                    dp[i] = dp[i - coinTypes[j]] + 1;
                    break;
                }
            }
            System.out.println(Arrays.toString(dp));
        }

        // 最少需要多少枚硬币
        return dp[money];
    }

    private static void dispState(int[][] states) {
        for (int[] state : states) {
            for (int i : state) {
                System.out.print(i);
                System.out.print("\t");
            }
            System.out.println();
        }
    }
}
