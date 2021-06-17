package com.cz.thread;

/**
 * ThreadLocal中，获取到线程私有对象是通过线程持有的一个threadLocalMap，然后传入ThreadLocal当做key获取到对象的，
 * 这时候就有个问题，如果你在使用完ThreadLocal之后，将其置为null，
 * 这时候这个对象并不能被回收，因为他还有 ThreadLocalMap->entry->key的引用，
 * 直到该线程被销毁，但是这个线程很可能会被放到线程池中不会被销毁，这就产生了内存泄露，
 * jdk是通过弱引用来解决的这个问题的，entry中对key的引用是弱引用，
 * 当你取消了ThreadLocal的强引用之后，他就只剩下一个弱引用了，所以也会被回收。
 * <p>
 * 作者：吕清海
 * 链接：https://www.zhihu.com/question/37401125/answer/337717256
 * 来源：知乎
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 *
 * @author alian
 * @date 2020/10/21 下午 5:28
 * @since JDK8
 */
public class ThreadLocalDemo {

    public static void main(String[] args) {

    }

    public static void testWeakMap() {

    }
}
