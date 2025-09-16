package com.cz.spring_cloud_gateway;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Stream;

public class TestReactor {

    @Test
    public void testMono() {
        System.out.println("主线程开始： " + System.currentTimeMillis());
        mono(Mono.just(1)).subscribe();
        System.out.println("主线程结束： " + System.currentTimeMillis());
    }

    public Mono<String> mono(Mono<Integer> in) {
        return in.flatMap(num -> {
            System.out.println("输入: " + num);
            System.out.println("三秒后响应");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return Mono.just("响应结果" + num);
        });
    }

    @Test
    public void test5() {
        Arrays.asList(1, 2, 3, 4)
                .stream()
                .map(testFunction())
                .forEach(System.out::println);
    }

    private Function<Integer, String> testFunction() {
        return num -> {
            return "结果" + num;
        };
    }

    @Test
    public void test4() throws InterruptedException {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        AtomicBoolean closed = new AtomicBoolean(false);

        Flux.generate(sink -> {
            try {
                if (closed.get()) {
                    sink.complete();
                }
                String value = queue.take();
                sink.next(value);
            } catch (Exception e){
                sink.error(e);
            }
        }).subscribeOn(Schedulers.elastic())
                .subscribe(item -> {
                    System.out.println("消费者获取值： " + item);
                });

        CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(() -> {
            int count = 0;
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    String value = "现在时间: " + System.currentTimeMillis();
                    System.out.println("工作线程放入值:" + value);
                    queue.put(value);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                count++;
                if (count == 10) {
                    closed.set(true);
                    countDownLatch.countDown();
                    return;
                }
            }
        }).start();

        countDownLatch.await();
    }

    @Test
    public void test3() {
        Flux.generate(sink -> {
            sink.next("Hello");
            sink.complete();
        }).subscribe(System.out::println);


        final Random random = new Random();
        Flux.generate(ArrayList::new, (list, sink) -> {
            int value = random.nextInt(100);
            list.add(value);
            sink.next(value);
            if( list.size() ==10 )
                sink.complete();
            return list;
        }).subscribe(System.out::println);
    }

    @Test
    public void test2() {
        Flux.create(sink -> {
                    sink.next(Thread.currentThread().getName());
                    sink.complete();
                }).publishOn(Schedulers.single())
                .map(x ->  String.format("[%s] %s", Thread.currentThread().getName(), x))
                .publishOn(Schedulers.elastic())
                .map(x -> String.format("[%s] %s", Thread.currentThread().getName(), x))
                .subscribeOn(Schedulers.parallel())
                .toStream()
                .forEach(System.out::println);

    }

    @Test
    public void test1() {
        //just()：创建Flux序列，并声明数据流，
        Flux<Integer> integerFlux = Flux.just(1, 2, 3, 4);//整形
        //subscribe()：订阅Flux序列，只有进行订阅后才回触发数据流，不订阅就什么都不会发生
        integerFlux.subscribe(System.out::println);
        
        Flux<String> stringFlux = Flux.just("hello", "world");//字符串
        stringFlux.subscribe(System.out::println);
        
        //fromArray(),fromIterable()和fromStream()：可以从一个数组、Iterable 对象或Stream 对象中创建Flux序列
        Integer[] array = {1,2,3,4};
        Flux.fromArray(array).subscribe(System.out::println);
        
        List<Integer> integers = Arrays.asList(array);
        Flux.fromIterable(integers).subscribe(System.out::println);
        
        Stream<Integer> stream = integers.stream();
        Flux.fromStream(stream).subscribe(System.out::println);
    }
}
