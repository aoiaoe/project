package leetcode;

/**
 * 外观数列, 即用计数法描述数字的组成 f(n + 1) 为描述 f(n)的字符串,
 *  其中n >= 1, 且f(1) = 1;
 * 举例:
 *      f(1) =  1
 *      f(2) = 描述f(1) = 1个1 = 1(1) = "11"
 *      f(3) = 描述f(2) = 2个1 = 2(2) = "21"
 *      f(4) = 描述f(3) = 1个2,1个1 = 1(2)1(1) = "1211"
 *      f(5) = 描述f(4) = 1个1, 1个2, 2个1 = 1(1)1(2)2(1) = "111221"
 *
 *  说到底: 就是用口语描述顺序描述一个数字字符串由几个什么数字组成
 *      比如: 35555 这串字符串由 一个3四个4组成  结果就是 1345
 *
 *  骚操作：
 *      当数据量较小的时候，可以使用枚举，枚举所有结果，然后o(1)的返回，称为查表法
 */
public class _38_CountAndSay {

    public static void main(String[] args) {
        System.out.println(countAndSay(4));
    }


    public static String countAndSay(int n) {
        String str = "1";
        for (int i = 2; i <= n; ++i) {
            StringBuilder sb = new StringBuilder();
            int start = 0;
            int pos = 0;

            while (pos < str.length()) {
                while (pos < str.length() && str.charAt(pos) == str.charAt(start)) {
                    pos++;
                }
                sb.append(Integer.toString(pos - start)).append(str.charAt(start));
                start = pos;
            }
            str = sb.toString();
        }

        return str;
    }

//    作者：力扣官方题解
//    链接：https://leetcode.cn/problems/count-and-say/
//    来源：力扣（LeetCode）
//    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
}
