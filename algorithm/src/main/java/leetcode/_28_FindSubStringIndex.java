package leetcode;

/**
 * 找寻模式串在主串中的起始位置
 *  -> 其实就是字符串匹配算法
 *      -> BF、RK、BM、KMP等
 */
public class _28_FindSubStringIndex {

    public static void main(String[] args) {
        System.out.println(strStr("abcd", "cbc"));
    }

    public static int strStr(String haystack, String needle) {
        int len1 = haystack.length(), len2 = needle.length();
        if(len1 < len2){
            return -1;
        }
        int j = 0;
        boolean flag = true;
        for (int i = 0; i <= len1 - len2; i++) {
            j = 0;
            flag = true;
            while (j < len2){
                if(haystack.charAt(j + i) != needle.charAt(j)){
                    flag = false;
                }
                j++;
            }
            if(flag){
                return i;
            }
        }
        return -1;
    }
}
