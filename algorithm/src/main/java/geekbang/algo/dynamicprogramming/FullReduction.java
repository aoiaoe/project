package geekbang.algo.dynamicprogramming;

/**
 * @author jzm
 * @date 2023/4/11 : 10:21
 */
public class FullReduction {


    public static void main(String[] args) {
//        int values[] = {5, 7, 6, 7, 9, 8, 3, 5};
        int values[] = {43,26,34,73,74,28,56,89,43,25,67,47,58,74,57,24,53,48,19,35,53,24,53,24};
        fullReduction(values, values.length, 110);
        System.out.println("----");
        double11advance(values, values.length, 110);
    }

    /**
     * 动态规划计算满减
     *
     * @param values      物品价值数组
     * @param count       物品数量
     * @param reduceLimit 满减金额
     */
    public static void fullReduction(int[] values, int count, int reduceLimit) {
        // 此处用3*reduceLimit + 1来代表状态， 而不用reduceLimit+1
        // 是因为凑满减活动是求大于满减限制的最小值，而最小值是不确定的
        // 并且如果求出来的数字太大，按照常理来说，既然是参加活动来薅羊毛的，太大其实对用户不友好
        // 用户其实期待的是最好刚好凑到满减金额参加活动，数字越靠近满减金额最好
        int limit = 2 * reduceLimit;
        boolean[][] states = new boolean[count][limit + 1];
        // 处理第一个物品
        states[0][0] = true;
        if (values[0] <= limit) {
            states[0][values[0]] = true;
        }
        // 从第二个物品开始处理
        for (int i = 1; i < count; i++) {
            // 不凑当前商品
            for (int j = 0; j <= limit; j++) {
                if (states[i - 1][j]) {
                    states[i][j] = true;
                }
            }
            // 凑当前商品
            for (int j = 0; j < limit - values[i]; j++) {
                if (states[i - 1][j]) {
                    states[i][j + values[i]] = true;
                }
            }
        }
        if(limit<= 22) {
            dispStates(states);
        }
        // 计算是否存在大于满减金额的最小金额
        // 从大于等于满减金额的位置开始遍历
        int i;
        for (i = reduceLimit; i <= limit; i++) {
            if (states[count - 1][i]) {
                break;
            }
        }
        // 倒推出那些商品参与了凑满减
        for (int j = count - 1; j > 0; j--) {
            if (i - values[j] >= 0 && states[j - 1][i - values[j]]) {
                // 购买了这个商品，i减去当前物品价格
                System.out.println("凑：" + values[j]);
                i = i - values[j];
            }// else 没有购买当前商品，j--， i不变
        }
    }

    public static void dispStates(boolean[][] states) {
        for (boolean[] state : states) {
            for (boolean b : state) {
                if (b) {
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

    // ---------------极客时间-------------


    // items商品价格，n商品个数, w表示满减条件，比如200
    public static void double11advance(int[] items, int n, int w) {
        boolean[][] states = new boolean[n][3*w+1];//超过3倍就没有薅羊毛的价值了
        states[0][0] = true;  // 第一行的数据要特殊处理
        if (items[0] <= 3*w) {
            states[0][items[0]] = true;
        }
        for (int i = 1; i < n; ++i) { // 动态规划
            for (int j = 0; j <= 3*w; ++j) {// 不购买第i个商品
                if (states[i-1][j] == true) states[i][j] = states[i-1][j];
            }
            for (int j = 0; j <= 3*w-items[i]; ++j) {//购买第i个商品
                if (states[i-1][j]==true) states[i][j+items[i]] = true;
            }
        }

        int j;
        for (j = w; j < 3*w+1; ++j) {
            if (states[n-1][j] == true) break; // 输出结果大于等于w的最小值
        }
        if (j == 3*w+1) return; // 没有可行解
        for (int i = n-1; i >= 1; --i) { // i表示二维数组中的行，j表示列
            if(j-items[i] >= 0 && states[i-1][j-items[i]] == true) {
                System.out.print(items[i] + " "); // 购买这个商品
                j = j - items[i];
            } // else 没有购买这个商品，j不变。
        }
        if (j != 0) System.out.print(items[0]);
    }
}
