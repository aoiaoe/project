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
        String s = " ";
        System.out.println(lengthOfLongestSubstring(s));
    }

    /**
     * 滑动窗口寻找
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstringByMoveWindow(String s) {
        Set<Character> set = new HashSet<>();
        int index = 0;
        int matLength = 0;
        int length = 0;
        while (index < s.length()){
            if(set.contains(s.charAt(index))){
                matLength = matLength > length ? matLength : length;
                length = 0;
            } else {

            }
        }
        return matLength;
    }

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

            length = Math.max(length, end - start + 1);
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
