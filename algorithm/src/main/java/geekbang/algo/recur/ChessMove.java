package geekbang.algo.recur;

/**
 * @author jzm
 * @date 2023/4/11 : 14:32
 */
public class ChessMove {

    public static void main(String[] args) {
        int[][] ches = {
                {1,3,5,9},
                {2,1,3,4},
                {5,2,6,7},
                {6,8,4,3}
        };
        move(ches, 0, 0, 3, 0);
        System.out.println(path);

        minDistBT(0, 0, 0, ches, 3);
        System.out.println(minDist);
    }
    private static int path = Integer.MAX_VALUE;

    /**
     * 求解一个正方形棋盘，每一个格子里面都有一个值，一个棋子从左上角顶点走到右下角顶点的最短路径
     * 棋子每次只能向右或者向下移动一格
     * @param chess
     * @param i
     * @param j
     * @param weightHeight
     * @param curPath
     */
    public static void move(int[][] chess, int i, int j, int weightHeight, int curPath) {
        if (i == weightHeight && j == weightHeight) {
            curPath += chess[i][j];
            if (curPath < path) {
                path = curPath;
            }
            return;
        }
        if(i < weightHeight){
            move(chess, i+1, j, weightHeight, curPath + chess[i][j]);
        }
        if(j < weightHeight){
            move(chess, i, j+1, weightHeight, curPath + chess[i][j]);
        }
    }



    // --------------极客时间-----------------


    private static int minDist = Integer.MAX_VALUE; // 全局变量或者成员变量
    // 调用方式：minDistBacktracing(0, 0, 0, w, n);
    public static void minDistBT(int i, int j, int dist, int[][] w, int n) {
        // 到达了n-1, n-1这个位置了，这里看着有点奇怪哈，你自己举个例子看下
        if (i == n && j == n) {
            if (dist < minDist) minDist = dist;
            return;
        }
        if (i < n) { // 往下走，更新i=i+1, j=j
            minDistBT(i + 1, j, dist+w[i][j], w, n);
        }
        if (j < n) { // 往右走，更新i=i, j=j+1
            minDistBT(i, j+1, dist+w[i][j], w, n);
        }
    }
}
