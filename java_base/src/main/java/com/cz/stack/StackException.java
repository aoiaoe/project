package com.cz.stack;

/**
 * @author alian
 * @date 2021/2/7 下午 5:23
 * @since JDK8
 */
public class StackException {
    static int count = 0;
    static int num = 30000;
    static Long[] arr = new Long[num];

    public static void main(String[] args) {
        String s1 = new String("abc");
        s1 = s1.intern();
        String s = "abc";
        System.out.println(s==s1);// false
//        try {
//            stackOverFlow();
//        } catch (Throwable e) {
//            System.out.println(count);
//            e.printStackTrace();
//        }

//        oom();
    }


    public static void stackOverFlow() {
        count++;
        stackOverFlow();
    }

    public static void oom() {
        for (Long i = 0L; i < num; i++) {
            arr[i.intValue()] = i;
        }
    }
}
