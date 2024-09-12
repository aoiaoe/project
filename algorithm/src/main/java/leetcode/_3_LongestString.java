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
        String s = "aacsfwas120i345acavnry43rsacase1h5a12ahj68w4125gzxcvbnmaacsfwas120i345acavnry43rsacase1h5a12ahj68w4125gzxcvbnmaacsfwas120i345acavnry43rsacase1h5a12ahj68w4125gzxcvbnmaacsfwas120i345acavnry43rsacase1h5a12ahj68w4125gzxcvbnmaacsfwas120i345acavnry43rsacase1h5a12ahj68w4125gzxcvbnmaacsfwas120i345acavnry43rsacase1h5a12ahj68w4125gzxcvbnmaacsfwas120i345acavnry43rsacase1h5a12ahj68w4125gzxcvbnm,aacsfwas120i345acavnry43rsacase1h5a12ahj68w4125gzxcvbnmaacsfwas120i345acavnry43rsacase1h5a12ahj68w4125gzxcvbnm";
//        s = "aab";
//        System.out.println(lengthOfLongestSubstring(s));
        System.out.println(lengthOfLongestSubstring3(s));
    }


    /**
     * 滑动窗口
     * ：右边界不断往右走，并记录每个字符串和它的下一个位置
     * 如果右边界指向的字符出现在记录的字符中，则左边界需要收敛到该位置
     *
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
            // + 1是考虑边界情况，比如字符串中只有一个字符的时候, 不 +1 会导致长度错误计算为0
            length = Math.max(length, end - start + 1);
            // 保存当前字符，并记录其下一个索引
            map.put(c, end + 1);

        }
        return length;
    }


    /**
     * 滑动窗口
     *
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring2(String s) {
        int n = s.length(), len = 0;
        // char -> index
        Map<Character, Integer> map = new HashMap<>();
        // 边界都初始化为0
        int start = 0;
        int end = 0;
        for (; end < n; end++) {
            // 依次获取字符串中每个字符
            // 扩展右边界
            char alpha = s.charAt(end);
            // 如果字符已经存在于map中
            if (map.containsKey(alpha)) {
                // 重新计算起始位置
                // 收缩左边界
                start = Math.max(map.get(alpha), start);
            }
            // 计算最长长度
            len = Math.max(len, end - start + 1);

            map.put(alpha, end + 1);
        }
        return len;
    }

    public static int lengthOfLongestSubstring3(String s) {
        //记录字符上一次出现的位置
        int[] last = new int[128];
        for (int i = 0; i < 128; i++) {
            last[i] = -1;
        }
        int res = 0;
        int start = 0;
        for (int i = 0; i < s.length(); i++) {
            int index = s.charAt(i);
            start = Math.max(start, last[index] + 1);
            res = Math.max(res, i - start + 1);
            last[index] = i;
        }
        return res;
    }


}
