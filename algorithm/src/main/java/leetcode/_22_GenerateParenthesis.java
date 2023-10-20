package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 生成有效括号对
 */
public class _22_GenerateParenthesis {

    public static void main(String[] args) {
        System.out.println(generateParenthesisByHand(3));
    }


    public static List<String> generateParenthesisByHand(int n){
        if(n == 0){
            return res;
        }
        generateByHand("", n, n);
        return res;
    }

    /**
     *
     * @param pair      当前括号对字符串
     * @param left      左括号计数器
     * @param right     右括号计数器
     */
    public static  void generateByHand(String pair, int left, int right){
        if(left == 0 && right == 0){
            res.add(pair);
            return;
        }
        // 如果左右相等，则添加一个左括号
        // 那边添加了一个括号则对应计数器需要-1
        if(left == right){
            generateByHand(pair + "(", left - 1, right);
        } else {
            // 因为如果相等，则添加左括号,所以只可能存在左括号更少的情况
            // 只需要判断左括号是否小于0
            if(left > 0){
                generateByHand(pair + "(", left - 1, right);
            }
           generateByHand(pair + ")", left, right - 1);
        }

    }



    //--------------------------------力扣题解

    static List<String> res = new ArrayList<>();
    public static List<String> generateParenthesis(int n) {
        if(n <= 0){
            return res;
        }
        getParenthesis("",n,n);
        return res;
    }

    private  static void getParenthesis(String str,int left, int right) {
        if(left == 0 && right == 0 ){
            res.add(str);
            return;
        }
        if(left == right){
            //剩余左右括号数相等，下一个只能用左括号
            getParenthesis(str+"(",left-1,right);
        }else if(left < right){
            //剩余左括号小于右括号，下一个可以用左括号也可以用右括号
            if(left > 0){
                getParenthesis(str+"(",left-1,right);
            }
            getParenthesis(str+")",left,right-1);
        }
    }
}
