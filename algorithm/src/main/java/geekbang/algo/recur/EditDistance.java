package geekbang.algo.recur;

/**
 * @author jzm
 * @date 2023/4/12 : 09:44
 */
public class EditDistance {

    private static int MIN_DIST = Integer.MAX_VALUE;

    public static void main(String[] args) {
        String s1 = "abcdef";
        String s2 = "aghijkl";
        System.out.println("手写莱温斯坦编辑距离： " + lwstEditDistance(s1.toCharArray(), s2.toCharArray(), 0, 0, 6, 7, 0));

        EditDistance ins = new EditDistance(s1, s2);
        ins.lwstBT(0, 0, 0);
        System.out.println("极客时间莱温斯坦编辑距离： " + ins.minDist);
    }

    /**
     * 莱温斯坦编辑距离
     * 回溯算法求解
     *
     * @param ch1 字符串数组
     * @param ch2
     * @param i   字符串1索引
     * @param j
     * @param m   字符串1长度
     * @param n
     * @return
     */
    public static int lwstEditDistance(char[] ch1, char[] ch2,
                                       int i, int j,
                                       int m, int n,
                                       int dist) {
        // 某一个字符串已经比较到最后了
        if (i == m || j == m) {
            // 分别将某个字符串剩下的长度加到编辑距离里面
            if (i < m) dist += (m - i);
            if (j < n) dist += (n - j);
            return dist;
        }
        // 两个字符相同, 跳过当前字符串，但是编辑距离不变
        if (ch1[i] == ch2[j]) {
            return lwstEditDistance(ch1, ch2, i + 1, j + 1, m, n, dist);
        }
        // 删除ch1[i]或者给ch2[j]前面添加一个字符
        // 就是ch1的索引向后移动，就相当于删除了ch1中的字符，同理，也相当于给ch2中增加了一个字符
        int res1 = lwstEditDistance(ch1, ch2, i + 1, j, m, n, dist + 1);
        // 删除ch2[j]或者给ch1[j]前面添加一个字符
        // 同上
        int res2 = lwstEditDistance(ch1, ch2, i, j + 1, m, n, dist + 1);
        // 将ch1[i]和ch2[j]替换为相同的字符, 就是跳过，但是不同于相同，需要将编辑距离+1
        int res3 = lwstEditDistance(ch1, ch2, i + 1, j + 1, m, n, dist + 1);
        return Math.min(Math.min(res1, res2), res3);
    }



    // --------极客时间-------

    public EditDistance(String s1, String s2){
        a = s1.toCharArray();
        b = s2.toCharArray();
        n = a.length;
        m = b.length;
    }
    private char[] a = "mitcmu".toCharArray();
    private char[] b = "mtacnu".toCharArray();
    private int n = a.length;
    private int m = b.length;
    private int minDist = Integer.MAX_VALUE; // 存储结果
    // 调用方式 lwstBT(0, 0, 0);
    public void lwstBT(int i, int j, int edist) {
        if (i == n || j == m) {
            if (i < n) edist += (n-i);
            if (j < m) edist += (m - j);
            if (edist < minDist) minDist = edist;
            return;
        }
        if (a[i] == b[j]) { // 两个字符匹配
            lwstBT(i+1, j+1, edist);
        } else { // 两个字符不匹配
            lwstBT(i + 1, j, edist + 1); // 删除a[i]或者b[j]前添加一个字符
            lwstBT(i, j + 1, edist + 1); // 删除b[j]或者a[i]前添加一个字符
            lwstBT(i + 1, j + 1, edist + 1); // 将a[i]和b[j]替换为相同字符
        }
    }
}
