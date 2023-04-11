package geekbang.algo.dynamicprogramming;

/**
 * 动态规划求解背包问题
 *
 * @author jzm
 * @date 2023/4/10 : 15:31
 */
public class Bag {

    public static void main(String[] args) {
        int[] weight = {2, 2, 4, 6, 3};
        int[] weight2 = {1, 1};
        System.out.println(new Bag().knapsack(weight, 5, 9));
        System.out.println(new Bag().bag(weight, 5, 9));
        System.out.println(new Bag().bag2(weight2, weight2.length, 19));

        int[] values = {3, 4, 8, 9, 6};
        System.out.println("极客时间动态规划求解背包价值：" + knapsack3(weight, values, weight.length, 10));
        System.out.println("手写动态规划求解背包价值：" + new Bag().bag3(weight, values, weight.length, 10));
    }

    /**
     * 动态规划求解0-1背包问题，只求解最大重量
     *
     * @param items          物品的重量数组
     * @param count          物品的个数
     * @param bagWeightLimit 背包重量限制
     * @return
     */
    public int bag(int[] items, int count, int bagWeightLimit) {
        // 申请二维数组记录每一个物品的不同状态
        boolean[][] state = new boolean[count][bagWeightLimit + 1];
        // 处理第一个物品
        state[0][0] = true;
        if (items[0] <= bagWeightLimit) {
            state[0][items[0]] = true;
        }
        for (int i = 1; i < count; i++) {
            // 不放进背包求解重量
            for (int j = 0; j <= bagWeightLimit; j++) {
                if (state[i - 1][j])
                    state[i][j] = true;
            }
            // 放进背包求解重量
            for (int j = 0; j <= bagWeightLimit - items[i]; j++) {
                if (state[i - 1][j]) {
                    state[i][j + items[i]] = true;
                }
            }
        }
        // 遍历最后一个数组，找到最大的重量
        displayState(state);
        for (int i = bagWeightLimit; i >= 0; i--) {
            if (state[count - 1][i]) {
                return i;
            }
        }
        return 0;
    }

    /**
     * 动态规划求解0-1背包问题，只求解最大重量
     *
     * @param items          物品的重量数组
     * @param count          物品的个数
     * @param bagWeightLimit 背包重量限制
     * @return
     */
    public int bag2(int[] items, int count, int bagWeightLimit) {
        // 使用一维数组记录当前物品的状态
        // 下一个物品状态依赖于上一个物品的状态
        boolean[] state = new boolean[bagWeightLimit + 1];
        // 处理第一个物品
        state[0] = true;
        if (items[0] <= bagWeightLimit) {
            state[items[0]] = true;
        }
        for (int i = 1; i < count; i++) {
            // 不用求解不放进背包时的重量
            // 因为状态数组此时是记录上一个物品状态
            // 当前物品不放进背包，就是上一个物品的状态
            // 只需要求解当前物品放进背包的

            // 不能从小到大循环， 会出现级联错误
            // 例如items = {1,1}, bagWeightLimit = 10
            // 第一个物品状态为 state[0] = state[1]  = true;
            // 而第二个物品期望状态为 state[0] = state[1] = state[2] = true
            // 但是因为在j = 1的时候，修改了state[2] = true
            // 在去循环j=2的时候, 发现state[2] = true, 于是修改了state[3] = true
            // 这是不对的
//            for (int j = 0; j <= bagWeightLimit - items[i]; j++) {
//                if (state[j]) {
//                    state[j + items[i]] = true;
//                }
//            }
            for (int j = bagWeightLimit - items[i]; j >= 0; j--) {
                if (state[j]) {
                    state[j + items[i]] = true;
                }
            }
        }
        // 遍历最后一个数组，找到最大的重量
        for (int i = bagWeightLimit; i >= 0; i--) {
            if (state[i]) {
                return i;
            }
        }
        return 0;
    }

    /**
     * 动态规划求解0-1背包中物品最大价值
     *
     * @param weights     重量数组
     * @param values      价值数组
     * @param weightLimit 背包重量限制
     * @return
     */
    public int bag3(int[] weights, int[] values, int count, int weightLimit) {
        // 状态数组： 索引代表当前背包中重量， 索引中的值代表价值
        int[] state = new int[weightLimit + 1];
        // 初始化状态数组
        // 因为0代表第一个物品不装进背包，所以空就不能使用数组默认值0来代表，而应该使用其他值，此处使用-1
        for (int i = 0; i < state.length; i++) {
            state[i] = -1;
        }
        // 处理第一个物品
        state[0] = 0;
        if(weights[0] <= weightLimit){
            state[weights[0]] = values[0];
        }

        for (int i = 1; i < count; i++) {
            for (int j = weightLimit - weights[i]; j >= 0; j--) {
                // 注意状态数组中空值使用-1代表
                if (state[j] != -1 && (state[j + weights[i]]) < (state[j] + values[i])) {
                    state[j + weights[i]] = state[j] + values[i];
                }
            }
        }
        // 应该遍历数组求最大值，因为有可能重量小，但是价值大，从而导致最大值在中间
        int max = -1;
        for (int i = 0; i < state.length; i++) {
            if(state[i] > max){
                max = state[i];
            }
        }
        return max;
    }
    // --------------------极客时间--------------------

    // weight:物品重量，n:物品个数，w:背包可承载重量
    // ，只求解最大重量
    public int knapsack(int[] weight, int n, int w) {
        boolean[][] states = new boolean[n][w + 1]; // 默认值false
        states[0][0] = true;  // 第一行的数据要特殊处理，可以利用哨兵优化
        if (weight[0] <= w) {
            states[0][weight[0]] = true;
        }
        for (int i = 1; i < n; ++i) { // 动态规划状态转移
            for (int j = 0; j <= w; ++j) {// 不把第i个物品放入背包
                if (states[i - 1][j] == true)
                    states[i][j] = states[i - 1][j];
            }
            // 因为背包重量限制为w, 而又需要加上当前循环的第i个物品
            // 所以只需要循环w-weight[i]次，因为如果再循环，重量就超过w，无意义了
            for (int j = 0; j <= w - weight[i]; ++j) {//把第i个物品放入背包
                if (states[i - 1][j] == true)
                    states[i][j + weight[i]] = true;
            }
        }
        displayState(states);
        for (int i = w; i >= 0; --i) { // 输出结果
            if (states[n - 1][i] == true)
                return i;
        }
        return 0;
    }

    public static void displayState(boolean[][] state) {
        for (int i = 0; i < state.length; i++) {
            for (int i1 = 0; i1 < state[i].length; i1++) {
                if (state[i][i1]) {
                    System.out.print("O");
                    System.out.print("\t");
                } else {
                    System.out.print("-");
                    System.out.print("\t");
                }
            }
            System.out.println();
        }
    }

    public static void displayState(int[][] state) {
        for (int i = 0; i < state.length; i++) {
            for (int i1 = 0; i1 < state[i].length; i1++) {
                if (state[i][i1] != -1) {
                    System.out.print(state[i][i1]);
                    System.out.print("\t");
                } else {
                    System.out.print("-");
                    System.out.print("\t");
                }
            }
            System.out.println();
        }
    }


    public static int knapsack3(int[] weight, int[] value, int n, int w) {
        int[][] states = new int[n][w+1];
        for (int i = 0; i < n; ++i) { // 初始化states
            for (int j = 0; j < w+1; ++j) {
                states[i][j] = -1;
            }
        }
        states[0][0] = 0;
        if (weight[0] <= w) {
            states[0][weight[0]] = value[0];
        }
        for (int i = 1; i < n; ++i) { //动态规划，状态转移
            for (int j = 0; j <= w; ++j) { // 不选择第i个物品
                if (states[i-1][j] >= 0) states[i][j] = states[i-1][j];
            }
            for (int j = 0; j <= w-weight[i]; ++j) { // 选择第i个物品
                if (states[i-1][j] >= 0) {
                    int v = states[i-1][j] + value[i];
                    if (v > states[i][j+weight[i]]) {
                        states[i][j+weight[i]] = v;
                    }
                }
            }
        }
        // 找出最大值
        int maxvalue = -1;
        displayState(states);
        for (int j = 0; j <= w; ++j) {
            if (states[n-1][j] > maxvalue) maxvalue = states[n-1][j];
        }
        return maxvalue;
    }
}
