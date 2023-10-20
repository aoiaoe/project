package leetcode;

public class _14_LongestCommonPrefix {

    public static void main(String[] args) {
        String[] s = {"flower","flow","flight"};
        System.out.println(longestCommonPrefix(s));
    }

    public static String longestCommonPrefix(String[] strs) {
        int len = strs.length;
        int len1 = Integer.MAX_VALUE;
        int tmplen;
        for (int j = 0; j < len; j++) {
            if ((tmplen = strs[j].length()) == 0) {
                return "";
            } else if (tmplen < len1) {
                len1 = tmplen;
            }
        }

        int i;
        char tmp;
        boolean flag = false;
        for (i = 0; i < len1; i++) {
            tmp = strs[0].charAt(i);
            for (int j = 1; j < len; j++) {
                if (strs[j].charAt(i) != tmp) {
                    flag = true;
                    break;
                }
            }
            if(flag){
                break;
            }
        }
        return strs[0].substring(0, i);
    }
}
