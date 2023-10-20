package leetcode;

public class _8_MyA2i {

    public static void main(String[] args) {
        System.out.println(myAtoi("-"));
    }
    public static int myAtoi(String s) {
        s = s.trim();
        if(s.length() < 1){
            return 0;
        }

        if (!Character.isDigit(s.charAt(0))
                && s.charAt(0) != '-' && s.charAt(0) != '+')
            return 0;
        char tmp = s.charAt(0);
        int symbol = tmp == '-' ? -1 : 1;
        int start = -1;
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            if((tmp = s.charAt(i)) >= 48 && tmp <= 57){
                start = i;
                break;
            } else if(tmp == '+' || tmp == '-'){
                start = i + 1;
                break;
            }
        }

        for (int i = start; i < s.length(); i++) {
            if((tmp = s.charAt(i)) >= 48 && tmp <= 57){
                res = res * 10 + ((int)tmp - 48);
                if(res < Integer.MIN_VALUE){
                    return Integer.MIN_VALUE;
                } else if(res > Integer.MAX_VALUE / 10){
                    return Integer.MAX_VALUE;
                }

            } else {
                break;
            }
        }
        return res * symbol;
    }
}
