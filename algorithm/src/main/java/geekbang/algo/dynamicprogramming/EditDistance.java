package geekbang.algo.dynamicprogramming;

/**
 * @author jzm
 * @date 2023/4/12 : 12:49
 */
public class EditDistance {

    public static void main(String[] args) {
        String s1 = "111";
        String s2 = "1111";
        System.out.println("莱温斯坦编辑距离： " + lwstEditDistance(s1, s2));
        System.out.println("最长公共子串编辑距离： " + longestCommonSubStringEditDist(s1, s2));

    }

    /**
     * 动态规划求解莱温斯坦编辑距离
     * 状态数组中, i,j位置的状态由三个位置中的"最小值"来，分别是[i-1,j],[i,j-1],[i-1,j-1]
     * 而每次又分为两种情况，分别是i,j索引对应的字符相等或者不相等
     * 只有在相等的时候，[i-1][j-1]直接跃迁到[i,j]不需要操作，
     * 而其他所有情况均需要一步操作
     *
     * 所以状态方程式为：
     *      if(ch1[i] == ch2[j]) {
     *          state[i][j] = Math.min(Math.min(state[i-1][j] + 1, state[i][j-1] + 1), state[i-1][j-1]);
     *      } else {
     *          state[i][j] = Math.min(Math.min(state[i-1][j] + 1, state[i][j-1] + 1), state[i-1][j-1] + 1);
     *      }
     *
     * @param s1
     * @param s2
     * @return
     */
    public static int lwstEditDistance(String s1, String s2) {
        char ch1[] = s1.toCharArray();
        char ch2[] = s2.toCharArray();
        int m = ch1.length;
        int n = ch2.length;
        int state[][] = new int[m][n];
        // 处理第一行，代表第二个字符串的所有值 与 第一个字符串的第一个字符的编辑距离
        for (int i = 0; i < n; i++) {
            // 如果相等，则等于索引的编辑距离
            if (ch1[0] == ch2[i]) {
                state[0][i] = i;
            } else if (i != 0) {
                state[0][i] = state[0][i - 1] + 1;
            } else {
                state[0][0] = 1;
            }
        }
        // 处理第一列，代表第一个字符串的所有字符 与 第二个字符串的第一个字符的编辑距离
        for (int i = 0; i < m; i++) {
            // 如果相等，则等于索引的编辑距离
            if (ch1[i] == ch2[0]) {
                state[i][0] = i;
            } else if (i != 0) {
                state[i][0] = state[i - 1][0] + 1;
            } else {
                state[0][0] = 1;
            }
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if(ch1[i] == ch2[j]) {
                    state[i][j] = Math.min(Math.min(state[i-1][j] + 1, state[i][j-1] + 1), state[i-1][j-1]);
                } else {
                    state[i][j] = Math.min(Math.min(state[i-1][j] + 1, state[i][j-1] + 1), state[i-1][j-1] + 1);
                }
            }
        }
        System.out.println("莱文斯坦编辑距离状态数组：");
        dispStates(state);
        return state[m-1][n-1];
    }

    public static int longestCommonSubStringEditDist(String s1, String s2){
        char ch1[] = s1.toCharArray();
        char ch2[] = s2.toCharArray();
        int m = ch1.length;
        int n = ch2.length;
        int state[][] = new int[m][n];

        for (int i = 0; i < n; i++) {
            if(ch1[0] == ch2[i]){
                state[0][i] = 1;
            } else if(i > 0){
                state[0][i] = state[0][i-1];
            }
        }
        for (int i = 0; i < m; i++) {
            if(ch1[i] == ch2[0]){
                state[i][0] = 1;
            } else if(i > 0){
                state[i][0] = state[i-1][0];
            }
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if(ch1[i] == ch2[j]){
                    state[i][j] = Math.max(state[i-1][j], Math.max(state[i][j-1], state[i-1][j-1] + 1));
                } else {
                    state[i][j] = Math.max(state[i-1][j], Math.max(state[i][j-1], state[i-1][j-1]));
                }
            }
        }
        System.out.println("最长公共子串编辑距离状态数组：");
        dispStates(state);
        return state[m-1][n-1];
    }

    public static void dispStates(int[][] states) {

        for (int[] state : states) {
            for (int b : state) {
                System.out.print(b);
                System.out.print("\t");
            }
            System.out.println();
        }
    }
}
