package geekbang.algo.dp;

import java.util.Arrays;

public class Bag {


    public static void main(String[] args) {
        int[] weight = {2,2,4,6,3};
        int[] values = {3,4,8,9,6};
        System.out.println(knapsack3(weight, values, weight.length, 9));
        System.out.println(bag(weight, values, weight.length, 9));
    }

    public static int knapsack3(int[] weight, int[] value, int n, int w) {
        int[][] states = new int[n][w + 1];
        for (int i = 0; i < n; ++i) { // 初始化states
            for (int j = 0; j < w + 1; ++j) {
                states[i][j] = -1;
            }
        }
        states[0][0] = 0;
        if (weight[0] <= w) {
            states[0][weight[0]] = value[0];
        }
        for (int i = 1; i < n; ++i) { //动态规划，状态转移
            for (int j = 0; j <= w; ++j) { // 不选择第i个物品
                if (states[i - 1][j] >= 0) states[i][j] = states[i - 1][j];
            }
            for (int j = 0; j <= w - weight[i]; ++j) { // 选择第i个物品
                if (states[i - 1][j] >= 0) {
                    int v = states[i - 1][j] + value[i];
                    if (v > states[i][j + weight[i]]) {
                        states[i][j + weight[i]] = v;
                    }
                }
            }
        }
        // 找出最大值
        int maxvalue = -1;
        disp(states);
        for (int j = 0; j <= w; ++j) {
            if (states[n - 1][j] > maxvalue) maxvalue = states[n - 1][j];
        }
        return maxvalue;
    }

    public static int bag(int[] weights, int[] values, int count, int limit) {
        int[] state = new int[limit + 1];
        // 初始化数组
        for (int i = 0; i < state.length; i++) {
            // 不能使用默认值0, 需要使用-1,因为存在0代表不放入物品的价值
            state[i] = -1;
        }
        state[0] = 0;
        if (weights[0] <= limit) {
            state[weights[0]] = values[0];
        }
        System.out.println(Arrays.toString(state));
        for (int i = 1; i < count; i++) {
            for (int j = limit - weights[i]; j >= 0; j--) {
                if (state[j] != -1 && (state[j + weights[i]]) < (state[j] + values[i])) {
                    state[j + weights[i]] = state[j] + values[i];
                }
            }
            System.out.println(Arrays.toString(state));
        }
        int max = 0;
        for (int i = 0; i < limit + 1; i++) {
            if(state[i] > max){
                max = state[i];
            }
        }
        return max;
    }

    public static void disp(int[][] state){
        System.out.println("状态数组:---------");
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if(state[i][j] != -1){
                    System.out.print(state[i][j]);
                    System.out.print("\t");
                } else {
                    System.out.print("-");
                    System.out.print("\t");
                }
            }
            System.out.println();
        }
        System.out.println("---------------");
    }
}
