package leetcode;

/**
 * 最长回文子串
 */
public class _5_LongestPalindrome {

    public static void main(String[] args) {
        String s = "asdfghjklkjhgfdsa";
//        System.out.println(longestPalindromeByDp(s));
        System.out.println(longestPalindrome(s));
        System.out.println(longestPalindromeByHand(s));
    }

    //-------------------------理解后手写解法-------------
    public static String longestPalindromeByHand(String s){
        int len = s.length();
        if(len < 2){
            return s;
        }
        int maxLen = 0;
        int start = 0;
        for (int i = 0; i < len - 1; i++) {
            // 判断当前回文子串是奇数个字符的长度
            int len1  = expandCenter(s, i, i);
            // 判断当前回文子串是偶数个字符的长度
            int len2 = expandCenter(s, i, i + 1);
            len1 = len1 > len2 ? len1 : len2;
            if(len1 > maxLen){
                start = i - (len1-1) / 2;
                maxLen = len1;
            }

        }
        return s.substring(start, start + maxLen);
    }

    public static int expandCenter(String s, int start, int end){
        while (start > -1 && end < s.length() && s.charAt(start) == s.charAt(end)){
            start--;
            end++;
        }
        // 此处-1: 当奇数个字符参数start=end,所以一定会经历一次循环，经历循环之后，长度差1，故-1
        //        当偶数个字符时，字符间距都相差1,故-1
        // 为什么是-1?
        //    因为在计算长度时，是用字符所在下标计算的，但是存在下标0，0占了一个计算位,
        return end - start - 1;
    }


    // ------------------------力扣中的解法------------------

    /**
     * 中心扩惨法
     * 从一个字符串的中心向外扩散判断是否为会问
     * @param s
     * @return
     */
    public static String longestPalindrome(String s){
        int len = s.length();
        if (len < 2) {
            return s;
        }
        int start = 0, end = 0;
        for (int i = 0; i < len - 1; i++) {
            // 奇数个字符
            int len1 = expandAroundCenter(s, i, i);
            System.out.print(i + "---" + len1 + "--");
            int len2 = expandAroundCenter(s, i, i + 1);
            System.out.print(len2);
            System.out.println("---");
            len1 = len1 > len2 ? len1 : len2;
            // 如果中心扩散检测大于一直最大长度，则计算起始下标
            if (len1 > end - start) {
                start = i - (len1 - 1) / 2;
                end = i + len1 / 2;
            }
//            作者：力扣官方题解
//            链接：https://leetcode.cn/problems/longest-palindromic-substring/solutions/255195/zui-chang-hui-wen-zi-chuan-by-leetcode-solution/
//            来源：力扣（LeetCode）
//            著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

        }
        return s.substring(start, end);
    }

    public static int expandAroundCenter(String s, int start, int end){
        while (start >= 0 && end < s.length() && s.charAt(start) == s.charAt(end)){
            start--;
            end++;
        }
        return end - start - 1;
    }
    /**
     * 动态规划求解
     * 一个回文的子串肯定也为回文
     */
    public static String longestPalindromeByDp(String s){
        int len = s.length();
        if (len < 2) {
            return s;
        }

        int maxLen = 1;
        int begin = 0;
        // dp[i][j] 表示 s[i..j] 是否是回文串
        boolean[][] dp = new boolean[len][len];
        // 初始化：所有长度为 1 的子串都是回文串
        for (int i = 0; i < len; i++) {
            dp[i][i] = true;
        }

        char[] charArray = s.toCharArray();
        // 递推开始
        // 先枚举子串长度
        for (int L = 2; L <= len; L++) {
            // 枚举左边界，左边界的上限设置可以宽松一些
            for (int i = 0; i < len; i++) {
                // 由 L 和 i 可以确定右边界，即 j - i + 1 = L 得
                int j = L + i - 1;
                // 如果右边界越界，就可以退出当前循环
                if (j >= len) {
                    break;
                }

                if (charArray[i] != charArray[j]) {
                    dp[i][j] = false;
                } else {
                    if (j - i < 3) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }

                // 只要 dp[i][L] == true 成立，就表示子串 s[i..L] 是回文，此时记录回文长度和起始位置
                if (dp[i][j] && j - i + 1 > maxLen) {
                    maxLen = j - i + 1;
                    begin = i;
                }
            }
        }
        return s.substring(begin, begin + maxLen);
//
//        作者：力扣官方题解
//        链接：https://leetcode.cn/problems/longest-palindromic-substring/solutions/255195/zui-chang-hui-wen-zi-chuan-by-leetcode-solution/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    }


}
