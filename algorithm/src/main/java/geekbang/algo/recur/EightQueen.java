package geekbang.algo.recur;

/**
 * 回溯算法求解八皇后问题, 类数独
 * 8x8的棋盘, 每一个位置放一个棋子，使每一行，每一列，每一个对角线，都只有一个棋子
 */
public class EightQueen {

    public static void main(String[] args) {
        new EightQueen().eightQueen(0);
    }

    // 存放棋子的位置
    // 索引为行, 值为列
    int[] res = new int[8];

    /**
     * 回溯计算棋子应该放置的位置
     *
     * @param row
     */
    public void eightQueen(int row) {
        if (row == 8) {
            print();
            return;
        }
        // 计算每一列应该存放的位置
        for (int column = 0; column < 8; column++) {
            if (ok(row, column)) {
                res[row] = column;
                eightQueen(row + 1);
            }
        }
    }

    /**
     * 计算棋子是否放置在此处是否正确
     * 因为数组索引是棋子所在行，值是棋子所在列
     * 所以只要res数组中数字唯一，就代表每一列只存在一个棋子
     * 但是需要进一步判断对角线
     *
     * @param row
     * @param column
     * @return
     */
    private boolean ok(int row, int column) {
        if(row == 0){
            return true;
        }
        // 从当前行往上逐行判断
        int left = column - 1, right = column + 1;
        for (int i = row - 1; i >= 0; i--) {
            // 判断列棋子唯一
            // 如果存在相同的数字，则说明不同的行，在同一列有棋子
            if(res[i] == column){
                return false;
            }
            // 判断对角线唯一
            if(res[i] == left || res[i] == right){
                return false;
            }
            left--;
            right++;
        }
        return true;
    }

    private void print() {
        System.out.println("打印:");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(j == res[i]){
                    System.out.print("Q");
                    System.out.print("\t");
                    continue;
                }
                System.out.print("-");
                System.out.print("\t");
            }
            System.out.println();
        }
    }
}
