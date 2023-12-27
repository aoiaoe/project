package com.cz.thread;


import com.cz.classloader.staticfield.C;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
public class CompletableFutureDemo {

    @Test
    public void testThenAccept(){
        CompletableFuture<String> call = call();
        log.info("1秒钟后获取结果");
        call.thenAccept(res -> {
            log.info("结果:{}", res);
        });
        call.join();
    }

    public CompletableFuture<String> call(){
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

                    int x = 1/0;
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
}
