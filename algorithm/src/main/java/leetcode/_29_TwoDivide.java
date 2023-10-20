package leetcode;

/**
 * 两数相除,
 * 要求 不使用 乘法、除法和取余运算。
 * 整数除法应该向零截断，也就是截去（truncate）其小数部分。例如，8.345 将被截断为 8 ，-2.7335 将被截断至 -2 。
 * 返回被除数 dividend 除以除数 divisor 得到的 商 。
 */
public class _29_TwoDivide {

    public static void main(String[] args) {
        System.out.println(divide(Integer.MIN_VALUE, 2));
    }

    /**
     * 暴力解法
     * @param dividend
     * @param divisor
     * @return
     */
    public static int divide(int dividend, int divisor) {
        int over = 0;
        if (dividend == Integer.MIN_VALUE) {
            if (divisor == -1) {
                return Integer.MAX_VALUE;
            }
            if (divisor == 1) {
                return Integer.MIN_VALUE;
            }
            dividend = dividend + 1;
            over = 1;
        }
        int symbol1 = dividend < 0 ? -1 : 1;
        int symbol2 = divisor < 0 ? -1 : 1;
        dividend = Math.abs(dividend);
        divisor = Math.abs(divisor);
        int res = 0;
        while (dividend >= divisor) {
            dividend -= divisor;
            if(over != 0){
                dividend += over;
            }
            res++;
        }
        return res * symbol1 * symbol2;
    }

}
