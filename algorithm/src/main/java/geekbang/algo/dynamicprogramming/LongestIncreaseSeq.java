package geekbang.algo.dynamicprogramming;

import java.util.Arrays;

/**
 * @author jzm
 * @date 2023/4/12 : 14:44
 */
public class LongestIncreaseSeq {


    public static void main(String[] args) {
        int[] arr = {2, 9, 3, 6, 5, 1, 7};
        System.out.println(incSeq(arr));
    }

    /**
     * 最长递增子序列
     *
     * @param arr
     * @return
     */
    public static int incSeq(int[] arr) {
        int n = arr.length;
        int st[] = new int[n];
        st[0] = 1;
        int maxSqLength = st[0];
        for (int i = 1; i < n; i++) {
            for (int j = i; j >= 0; j--) {
                if (arr[j] < arr[i] && st[j] >= st[i]) {
                    st[i] = st[j] + 1;
                    if (st[i] > maxSqLength) {
                        maxSqLength = st[i];
                    }
                }
            }

        }
        System.out.println(Arrays.toString(st));
        return maxSqLength;
    }
}
