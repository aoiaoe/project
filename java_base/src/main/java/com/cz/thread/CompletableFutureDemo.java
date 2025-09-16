package com.cz.thread;


import com.cz.classloader.staticfield.C;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
public class CompletableFutureDemo {

    @Test
    public void testThenAccept() {
        CompletableFuture<String> call = call();
        log.info("1秒钟后获取结果");
        call.thenAccept(res -> {
            log.info("结果:{}", res);
        });
        call.join();
    }

    public CompletableFuture<String> call() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "AAA";
        });
    }

    @Test
    public void testComplete() throws ExecutionException, InterruptedException {
        CompletableFuture<String> async = CompletableFuture.supplyAsync(() -> {
            log.info("睡眠1秒返回结果");
            try {
                SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "AAA";
        });
//        CompletableFuture<String> async = new CompletableFuture<>();
//        new Thread(() -> {
//            try {
//                log.info("睡眠1秒返回结果");
//                SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            async.complete("aaa");
//        }).start();
        log.info("获取结果:{}", async.get());
    }

    public static void main(String[] args) {
        log.info("task begin");
        Stream.<Runnable>of(
                () -> {
                    try {
                        SECONDS.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(1);
                },
                () -> {
                    try {
                        SECONDS.sleep(2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(2);
                },
                () -> {
                    try {
                        SECONDS.sleep(3);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(3);
                },
                () -> {

//                    int x = 1 / 0;
                    try {
                        SECONDS.sleep(4);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(4);
                }
        ).map(r -> CompletableFuture.runAsync(r).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        }))
                .collect(Collectors.toList())
                .stream()
                .map(CompletableFuture::join).collect(Collectors.toList());
        log.info("task end");
    }


    public static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            3, 50, 5, TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(10),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
    );

    /**
     * Completable 父子任务 + 使用同一个线程池， 可能导致死锁
     * 其实不只是Completable的问题，
     * 死锁条件：异步任务+父子任务+相同线程池
     * 必要条件: 父子任务使用相同线程池，父任务占用了全部线程，又提交子任务到线程池，且阻塞等待子任务完成
     *
     * @throws Exception
     */
    @Test
    public void testDeadLock() throws Exception {

        List<CompletableFuture<Void>> list = new ArrayList<>(0);
        for (int i = 0; i < 3; i++) {
            // 2、线程池中就3个空闲线程，因为做了 sleep，所以 3个资源都给了父任务
            // 死锁条件1: 异步任务
            CompletableFuture<Void> parentTask = CompletableFuture.runAsync(() -> {
                try {
                    System.out.println("父任务执行了：" + Thread.currentThread().getName());
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 3、子任务在等待父任务释放资源，父任务在等待子任务执行完，死锁
                // 死锁条件2：子任务
                CompletableFuture<Void> childTask = CompletableFuture.runAsync(() -> {
                    System.out.println("子任务执行了：" + Thread.currentThread().getName());
                }, threadPoolExecutor);
                // 死锁条件3：父任务等待子任务执行完成
                childTask.join();

                // 死锁条件4：父子异步任务使用相同线程池
            }, threadPoolExecutor);
            list.add(parentTask);
        }
        // 1、开始创建3个异步任务并执行
        CompletableFuture.allOf(list.toArray(new CompletableFuture[0])).get();
        threadPoolExecutor.shutdown();
        System.out.println("exit");
    }
}
