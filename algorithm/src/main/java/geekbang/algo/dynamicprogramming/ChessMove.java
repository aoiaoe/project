package geekbang.algo.dynamicprogramming;

/**
 * @author jzm
 * @date 2023/4/11 : 15:26
 */
public class ChessMove {

    public static void main(String[] args) {
        int[][] chess = {
                {1, 3, 5, 9},
                {2, 1, 3, 4},
                {5, 2, 6, 7},
                {6, 8, 4, 3}
        };


        System.out.println(move(chess, chess.length));
        System.out.println("递归求解： " + moveByRecursion(chess, 3, 3));
        System.out.println(minDistDP(chess, chess.length));

    }

    /**
     * 动态规划
     * 求解一个正方形棋盘，每一个格子里面都有一个值，一个棋子从左上角顶点走到右下角顶点的最短路径
     * 棋子每次只能向右或者向下移动一格
     *
     * @param chess
     * @return
     */
    public static int move(int chess[][], int weightHeight) {
        int[][] path = new int[weightHeight][weightHeight];
        // 初始化第一行及第一列的值
        int sum = 0;
        int sum2 = 0;
        for (int i = 0; i < weightHeight; i++) {
            sum += chess[0][i];
            path[0][i] = sum;
            sum2 += chess[i][0];
            path[i][0] = sum2;
        }
        // 从第一行第一列值开始遍历
        for (int i = 1; i < weightHeight; i++) {
            for (int j = 1; j < weightHeight; j++) {
                // 当前格子的值 = 棋盘上对应格子的值 + 之前计算的左边或者上边最小的值
                path[i][j] = chess[i][j] + Math.min(path[i - 1][j], path[i][j - 1]);
            }
        }
        System.out.println("手写算法");
        dispState(path);
        return path[weightHeight - 1][weightHeight - 1];
    }

    static int[][] mem = new int[4][4];
    /**
     * 递归求解棋盘左上角移动到右下角的最短路径
     * 问题可以分解为子问题,所以可以使用递归
     * 递归方程式为 minPath = chess[i][j] + Math.min(chess[i-1][j], chess[i][j-1]);
     * 因为棋子只能向右或者向下移动，所以当前位置的的路径等于，当前格子的值 + 左边或者上边之间最小的值
     *
     * @param chess
     * @param i
     * @param j
     * @return
     */
    public static int moveByRecursion(int[][] chess, int i, int j) {
        if (i == 0 && j == 0) {
            return chess[0][0];
        }
        // 判断当前节点是否处理过
        if(mem[i][j] != 0){
            return mem[i][j];
        }
        int upMin = Integer.MAX_VALUE;
        if (i - 1 >= 0) {
            upMin = moveByRecursion(chess, i - 1, j);
        }
        int leftMin = Integer.MAX_VALUE;
        if (j - 1 >= 0){
            leftMin = moveByRecursion(chess, i, j - 1);
        }
        // 记录处理过的节点
        mem[i][j] = chess[i][j] + Math.min(leftMin, upMin);
        return mem[i][j];
    }


    // ---------------极客时间---------------


    public static int minDistDP(int[][] matrix, int n) {
        int[][] states = new int[n][n];
        int sum = 0;
        for (int j = 0; j < n; ++j) { // 初始化states的第一行数据
            sum += matrix[0][j];
            states[0][j] = sum;
        }
        sum = 0;
        for (int i = 0; i < n; ++i) { // 初始化states的第一列数据
            sum += matrix[i][0];
            states[i][0] = sum;
        }
        for (int i = 1; i < n; ++i) {
            for (int j = 1; j < n; ++j) {
                states[i][j] =
                        matrix[i][j] + Math.min(states[i][j - 1], states[i - 1][j]);
            }
        }
        System.out.println("极客时间算法");
        dispState(states);
        return states[n - 1][n - 1];
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
