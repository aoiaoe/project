package geekbang.algo.arraylist.recursion;

/**
 * n级阶梯，每次走一步或这两步，一共有多少种走法
 */
public class StairStepCount {

    public static void main(String[] args) {
        System.out.println(climbStairsRecursion(10));
        System.out.println(climbStairs(10));
    }

    /**
     * 递归方式求解阶梯问题
     * @param n
     * @return
     */
    public static int climbStairsRecursion(int n){
        if(n == 2)
            return 2;
        else if (n == 1)
            return 1;
        return climbStairsRecursion(n - 1) + climbStairsRecursion(n - 2);
    }

    /**
     * 非递归求解爬楼梯问题，第n级阶梯的爬法 = 第n-1级阶梯爬法 + 第n-2阶梯爬法
     * @param n
     * @return
     */
    public static int climbStairs(int n){
        if (n == 1){
            return 1;
        }
        if(n == 2){
            return 2;
        }
        int res = 0;
        int f = 1;
        int ff = 2;
        for (int i = 3; i <= n; i++) {
            res = f + ff;
            f = ff;
            ff = res;
        }
        return res;
    }

}
