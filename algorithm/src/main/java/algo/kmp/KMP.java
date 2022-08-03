package algo.kmp;

public class KMP {

    public static void main(String[] args) {
        String source = "真后缀的最大长度，以获取模式串尽量多地往右移动";
        String target = "最大";
        kmp(source, target);
    }
    //构建next表
    public static int[] buildNext(String sub){
        //构建next表就是查找真前缀 == 真后缀的最大长度，以获取模式串尽量多地往右移动
        int[] next = new int[sub.length()];
        //主串位置
        int j = 0;
        //子串位置
        int t = next[0] = -1;

        while (j<sub.length()-1){
            if (t<0||sub.charAt(j)==sub.charAt(t)){
                j++;
                t++;
                next[j] = t;
            }else {
                t = next[t];
            }
        }
        return next;
    }

    public static void kmp(String parent,String sub){
        int[] next = buildNext(sub);
        //成功匹配的位置
        int index = -1;
        //主串的长度
        int pLen = parent.length();
        //子串的长度
        int sLen = sub.length();

        if (pLen<sLen){
            System.out.println("Error.The main string is greater than the sub string length.");
            return;
        }

        int i = 0;
        int j = 0;
        while (i<pLen&&j<sLen){
            // 判断对应位置的字符是否相等
            if (j==-1||parent.charAt(i)==sub.charAt(j)){
                //若相等.主串子串继续比较
                i++;
                j++;
            }else{
                //i不变,j=next[j]
                j = next[j];
            }
        }
        //匹配成功
        if (j >= sLen) {
            index = i - j;
            System.out.println("Successful match,index is:" + index);
        } else {// 匹配失败
            System.out.println("Match failed.");
        }
    }
}

