package niukewang.heap_stack_queue;

import java.util.Stack;

/**
 * 给出一个仅包含字符'(',')','{','}','['和']',的字符串，判断给出的字符串是否是合法的括号序列
 * 括号必须以正确的顺序关闭，"()"和"()[]{}"都是合法的括号序列，但"(]"和"([)]"不合法。
 */
public class _44_IsValidSymbol {

    public static void main(String[] args) {
        String s = "{}";
        System.out.println(isValid(s));
    }

    public static boolean isValid (String s) {
        // write code here
        if(s == null){
            return true;
        }
        Stack<Character> stack = new Stack<>();
        char ch = ' ';
        char top = ' ';
        int gap = -1;
        for (int i = 0; i < s.length(); i++) {
            ch = s.charAt(i);
            if(ch == '(' || ch == '{' || ch == '['){
                stack.push(ch);
                continue;
            }
            if(stack.empty()){
                return false;
            }
            top = stack.pop();
            gap = ch - top;
            // 因为只包含(){}[], 正常的括号对之间, ascii码相减,
            // ) 减去 ( 等于 1
            // } 减去 { 等于 2
            // ] 减去 [ 等于 2
            if(gap != 1 && gap != 2){
                return false;
            }
        }
        if(!stack.empty()){
            return false;
        }
        return true;
    }

    /**
     * 依次获取字符，
     * 如果是前括号往栈中添加后括号
     * 如果是后括号，则出栈比较, 不相等则括号不匹配
     * @param s
     * @return
     */
    public static boolean isValid1(String s) {
        Stack<Character> stack = new Stack<>();
        char ch = ' ';
        for (char c : s.toCharArray()) {
            if(c == '(')
                stack.push(')');
            else if(c == '[')
                stack.push(']');
            else if (c == '{')
                stack.push('}');
            else if(!stack.empty() && c != stack.peek()){
                return false;
            }
        }
        // 循环完，栈不空，则不匹配
        return stack.empty();
    }

}
