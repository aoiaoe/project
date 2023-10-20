package leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
 */
public class _3_LongestString {

    public static void main(String[] args) {
        String s = "aacsfwas12345acav";
        System.out.println(lengthOfLongestSubstring(s));
    }


    /**
     * 滑动窗口
     *      ：右边界不断往右走，并记录每个字符串和它的下一个位置
     *      如果右边界指向的字符出现在记录的字符中，则左边界需要收敛到该位置
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring(String s) {
        if (s == null) {
            return 0;
        }
        Map<Character, Integer> map = new HashMap<>();
        int length = 0;
        Character c = null;
        Integer temp = null;
        for (int start = 0, end = 0; end < s.length(); end++) {
            c = s.charAt(end);
            if ((temp = map.get(c)) != null) {
                start = Math.max(start, temp);
            }
            // 计算最长的长度
            length = Math.max(length, end - start + 1);
            // end + 1 ： 因为是如果存在相同的字符，则从此字符的下一个字符开始才不重复
            // 例如： aabc, 第一次循环，map中存放 a -> 1,
            // 第二次循环，能在map中获取到a，则就将窗口左边界设置为1

            // 上面解释还不如说是考虑边界情况： 比如说字符串只有一个字符
            map.put(c, end + 1);

        }
        return length;
    }

    public int lengthOfLongestSubstring2(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int end = 0, start = 0; end < n; end++) {
            char alpha = s.charAt(end);
            if (map.containsKey(alpha)) {
                start = Math.max(map.get(alpha), start);
            }
            ans = Math.max(ans, end - start + 1);
            map.put(alpha, end + 1);
        }
        return ans;
    }

}
