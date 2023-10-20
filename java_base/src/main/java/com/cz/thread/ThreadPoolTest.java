package com.cz.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolTest {

    public static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            3,50,5, TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(10),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
    ) ;

    public static void main(String[] args) throws Exception {

        List<CompletableFuture<Void>> list = new ArrayList<>(0) ;
        for(int i = 0 ; i < 3 ; i++) {
            // 2、线程池中就3个空闲线程，因为做了 sleep，所以 3个资源都给了父任务
            CompletableFuture<Void> parentTask = CompletableFuture.runAsync(() -> {
                try {
                    System.out.println("父任务执行了：" + Thread.currentThread().getName());
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 3、子任务在等待父任务释放资源，父任务在等待子任务执行完，死锁
                CompletableFuture<Void> childTask = CompletableFuture.runAsync(() -> {
                    System.out.println("子任务执行了：" + Thread.currentThread().getName());
                }, threadPoolExecutor);
                childTask.join() ;

            }, threadPoolExecutor);
            list.add(parentTask) ;
        }
        // 1、开始创建3个异步任务并执行
        CompletableFuture.allOf(list.toArray(new CompletableFuture[0])).get();
        threadPoolExecutor.shutdown();
        System.out.println("exit");
    }
}
