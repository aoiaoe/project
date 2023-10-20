package leetcode;

/**
 * N 字形变换
 * 将一个给定字符串 s 根据给定的行数 numRows ，以从上往下、从左到右进行 Z 字形排列。
 *
 * 比如输入字符串为 "PAYPALISHIRING" 行数为 3 时，排列如下：
 * P   A   H   N
 * A P L S I I G
 * Y   I   R
 * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："PAHNAPLSIIGYIR"。
 */
public class _6_N_CharChange {

    public static void main(String[] args) {
        System.out.println(19 / 4 * 2);
    }

    public static String convert(String s, int numRows) {
        int len;
        if(s == null || (len = s.length()) < 2){
            return s;
        }
//        int arrLen = len / 4 * 2;
        int arrLen = len / 2;
        arrLen = arrLen + (len % 4 != 0 ? 1 : 0);
        int[][] arr = new int[3][arrLen];

        // 没写完
        return null;
    }
}
