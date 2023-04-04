package geekbang.algo.ssearch;

/**
 * 数字开平方
 * @author jzm
 * @date 2023/3/29 : 10:26
 */
public class _8_SqrtNum {

    public static void main(String[] args) {
        System.out.println(sqrtNum(2));
    }
    public static double sqrtNum(double num){
        if(num < 0){
            throw new RuntimeException("must larger than zero");
        } else if(num == 1){
            return 1.000000;
        }
        int cycle = 50;
        double start = 0f;
        double end = num;
        double mid = 0;
        double res;
        while (cycle > 0){
            mid = (start + end) / 2;
            if((res = Math.pow(mid, 2)) == num){
                return mid;
            } else if( res > num){
                end = mid;
            } else {
                start = mid;
            }
            cycle--;
        }
        return mid;
    }
}
