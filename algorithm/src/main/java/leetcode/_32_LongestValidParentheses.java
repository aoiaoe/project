package leetcode;

import java.util.LinkedList;
import java.util.Stack;

/**
 * 最长有效括号
 * 给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。
 */
public class _32_LongestValidParentheses {

    public static void main(String[] args) {
        String[] arr = {"(()()","()(()","(()","(()))", ")", "))(())())())(()"};
        for (String s : arr) {
            System.out.println(s + " -> " + longestValidParentheses(s));
        }
    }

    /**
     * 对于遇到的每个 ‘(’\text{‘(’}‘(’ ，我们将它的下标放入栈中
     * 对于遇到的每个 ‘)’\text{‘)’}‘)’ ，我们先弹出栈顶元素表示匹配了当前右括号：
     *      如果栈为空，说明当前的右括号为没有被匹配的右括号，我们将其下标放入栈中来更新我们之前提到的「最后一个没有被匹配的右括号的下标」
     *      如果栈不为空，当前右括号的下标减去栈顶元素即为「以该右括号为结尾的最长有效括号的长度」
     *
     * @param s
     * @return
     */
    public static int longestValidParentheses(String s) {
        int maxans = 0;
        // 栈中存放的是: 未匹配到 ) 的 ( 所在的下标
        Stack<Integer> stack = new Stack<>();
        // 推入-1,防止第一个字符为 )
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    maxans = Math.max(maxans, i - stack.peek());
                }
            }
        }
        return maxans;
    }

//    作者：力扣官方题解
//    链接：https://leetcode.cn/problems/longest-valid-parentheses/
//    来源：力扣（LeetCode）
//    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
}
