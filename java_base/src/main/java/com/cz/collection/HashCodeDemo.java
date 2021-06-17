package com.cz.collection;

import org.openjdk.jol.vm.VM;

import java.util.HashSet;

/**
 * hashCode是用来加速哈希表的，在同一个java应用程序中，hashCode是对java对象的一个签名，相等的对象hashCode一定相等。
 * <p>
 * <p>
 * 每个对象都能返回一个hashCode，hashCode是用来加速哈希表的
 * 在同一java应用程序执行期间，对同一个对象调用超过一次时，如果equals()比较的内容没有发生变化，hashCode()的返回应该也不应该发生变化。
 * （特别需要注意的是同一java程序，不同java程序可以允许hashCode发生变化）
 * 根据equals()方法，两个对象相等，那么hashCode也必须要相等
 * 根据equals()方法，两个对象不相等，那么hashCode可能相等，但是要尽可能的让他们不相等，这样可以提升哈希表的性能
 * <p>
 * 可以完全避免hash冲突吗？
 * 答: 一个原理，解释不可能, hashCode返回的是一个int值，int值范围是有限的, 而对象却是无限的，
 * 故并不能完全避免hash冲突， 只能设计更好的算法减少冲突
 * <p>
 * 为什么重写equals方法一定要重写hashCode方法?
 * 答:根据java规范，equals()相等的对象，hashCode()必须相等,
 * 重写equals()必然导致对象是否相等的结果发生变化，因此也就需要修改hashCode()方法,
 * 如果只修改equals()不修改hashCode()方法，就会导致使用哈希表的时候不能准确定位到哈希桶，导致哈希表工作异常
 * <p>
 * 作者：CrazyMark
 * 链接：https://juejin.im/post/6893780715860262925
 * 来源：掘金
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 * <p>
 * 如果不重写hashcode方法,那么java默认的hashcode生成是由一套随机算法生成的，只与对象生成的顺序和线程有关。
 *
 * @author alian
 * @date 2020/11/12 下午 12:21
 * @since JDK8
 */
public class HashCodeDemo {

    public static void main(String[] args) throws InterruptedException {
//        A a = new A();
//        // 打印对象地址
//        System.out.println(a);
//        // 打印对象的hashCode
//        System.out.println(a.hashCode());
//        // 打印对象的hashCode转成16进制的字符串
//        System.out.println(Integer.toHexString(a.hashCode()));
        testHashCodeIndexDifferentThread();
    }

    /**
     * 测试对象的hashcode和对象的地址
     */
    public static void testHashCodeAndMemory() {
        for (int i = 0; i < 5; i++) {
            Object o = new Object();
            System.out.println("hashCode:" + o.hashCode());
            System.out.println("address:" + VM.current().addressOf(o));
        }
    }

    /**
     * 相同线程，测试hash生成顺序,多次运行发现hash冲突的结果都一样
     * 结论： 如果不重写hashcode方法,那么java默认的hashcode生成是由一套随机算法生成的，与对象生成的顺序有关。
     */
    public static void testHashCodeIndexSameThread() {
        HashSet<Integer> set = new HashSet<Integer>();
        int count = 0, num = 0;
        Object o = null;
        while (true) {
            count++;
            o = new Object();
            if (set.contains(o.hashCode())) {
                num++;
                System.out.println("count:" + count);
                System.out.println("hashCode:" + o.hashCode());
                if (num == 5) break;
            }
            set.add(o.hashCode());
            o = null;
        }
    }

    /**
     * 不同线程，测试hash生成顺序, 会发现每次都不一样
     * 结论:如果不重写hashcode方法,那么java默认的hashcode生成是由一套随机算法生成的，与
     * 对象生成的线程(或者说一个生成的随机数,但是main线程的随机数是固定的,所以每次启动main线程,结果都一样)有关。
     *
     * @throws InterruptedException
     */
    public static void testHashCodeIndexDifferentThread() throws InterruptedException {
        new Thread(() -> {
            HashSet<Integer> set = new HashSet<Integer>();
            int count = 0, num = 0;
            while (true) {
                count++;
                Object o = new Object();
                if (set.contains(o.hashCode())) {
                    num++;
                    System.out.println("count:" + count);
                    System.out.println("hashCode:" + o.hashCode());
                    if (num == 5) break;
                }
                set.add(o.hashCode());
            }
        }).start();
        Thread.sleep(1000L);
    }
}

class A {
}
