package algo.kmp;

import java.util.Arrays;

public class KmpAlgo {

    /**
     * 获取指定字符串str中从左至右依次+1增长的子字符串中的与前缀子字符串一一对应的中间子字符串的长度形成的数组
     *
     * @author yuni_xiumu 比如: getNext("asdfasdf3") = [0, 0, 0, 0, 0, 1, 2, 3, 4]
     * @param str
     * @return
     */
    public static int[] getNext(String str) {

        if (null == str || str.isEmpty())
            return new int[0];

        int[] next = new int[str.length()];// 自动默认填充0
        int j = 0;// 用于储存与str中的能够与前缀子字符串一一对应的中间子字符串的长度

        // 依次遍历str中的每一个元素(第一个和最后一个除外)
        for (int i = 1; i < str.length() - 1; i++) {
            if (str.charAt(i) == str.charAt(j)) {// 存在长度>=1的中间子字符串能够和前缀子字符串一一对应
                next[i + 1] = ++j;// 记录匹配情况 i+1:表示str中下标:i+1的左侧字符串中匹配情况
            } else {
                j = 0;// 一旦中间子字符串无法一一对应前缀子字符串，则置0，重新继续剩余子字符串与前缀子字符串的匹配判断
//				next[i+1] = 0;//因为Java中默认给该数组填充了0，所以可以省略该步骤
            }
        }

        return next;
    }

    /**
     * KMP算法实现查找父字符串中首次出现子字符串的位置
     *
     * @param parentStr
     * @param subStr
     * @return
     */
    public static int kmpSearch(String parentStr, String subStr) {

        int[] next = getNext(subStr);
        int parentStrIndex = 0;// 父字符串下标
        int subStrIndex = 0;// 子字符串下标

        while (parentStrIndex < parentStr.length()) {
            if (parentStr.charAt(parentStrIndex) == subStr.charAt(subStrIndex)) {
                subStrIndex++;
                if (subStrIndex == subStr.length()) {
                    return parentStrIndex - subStr.length() + 1;
                }
                parentStrIndex++;
            } else {
                // 核心点:用于加快匹配速度
                // 之前已经和子字符串匹配过了的可以适当跳过
                parentStrIndex += subStr.length() - next[subStrIndex];
            }
        }

        return -1;
    }

    public static void main(String args[]) {
        System.out.println(Arrays.toString(getNext("asdfasde3")));
        String str1 = "ab-a--aba--aba";
        String find = "abae";
        System.out.println(kmpSearch(str1, find));
        System.out.println("over");
    }

}