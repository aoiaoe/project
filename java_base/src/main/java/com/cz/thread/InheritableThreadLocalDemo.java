package com.cz.thread;

/**
 * InheritableThreadLocal可以使子线程获取到父线程的资源
 * 但是如果是系统之间调用,可以使用TransmittableThreadLocal类
 */
public class InheritableThreadLocalDemo {
    static InheritableThreadLocal<String> INHERITABLE_THREAD_LOCAL = new InheritableThreadLocal();
    static ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();
    public static void main(String[] args) throws InterruptedException {
        INHERITABLE_THREAD_LOCAL.set("父线程中使用 InheritableThreadLocal 设置变量");
        THREAD_LOCAL.set("父线程中使用 ThreadLocal 设置变量");
        Thread thread = new Thread(
                () -> {
                    // 能拿到设置的变量
                    System.out.println("从 InheritableThreadLocal 拿父线程设置的变量: " + INHERITABLE_THREAD_LOCAL.get());
                    // 打印为 null
                    System.out.println("从 ThreadLocal 拿父线程设置的变量: " + THREAD_LOCAL.get());
                }
        );
        thread.start();
        thread.join();
    }
}