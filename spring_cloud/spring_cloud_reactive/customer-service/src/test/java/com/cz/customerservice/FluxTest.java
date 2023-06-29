package com.cz.customerservice;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

public class FluxTest {

    @Test
    public void test() {

//        Flux.fromArray(new Integer[]{1,2,3}).subscribe(System.out::println);
//        Flux.generate(sink -> {
//            sink.next("jzm");
//            sink.complete();
//        }).subscribe(System.out::println);
//        Mono.just("cz")
//                // 抛出异常
//                .concatWith(Mono.error(new IllegalStateException()))
//                // 容错策略，返回默认值
//                .onErrorReturn("czerror")
//                .subscribe(System.out::println);

//        Flux.range(1, 25).buffer(10).subscribe(System.out::println);
//        Flux.range(1, 5).window(2).toIterable().forEach(w -> {
//            w.subscribe(System.out::println);
//            System.out.println("---");
//        });

//        Flux.just(1,2).map(i -> "number:" + i).subscribe(System.out::println);
//
//        Flux.just(1,5).flatMap(x -> Mono.just(x * x))
//                .subscribe(System.out::println);

        Mono.just("CZ").flatMap(e -> Mono.just("cczz")).subscribe(System.out::println);

        // 过滤
//        Flux.range(1,10).filter(i -> i % 2 == 0)
//                .subscribe(System.out::println);

//        Flux.range(1, 100).take(10).subscribe(System.out::println);
//        Flux.range(1, 100).takeLast(10).subscribe(System.out::println);

    }

    @Test
    public void test1() {
//        Flux.just(1,2,3).then().subscribe(System.out::println);

//        Flux.just(1,2,3).thenMany(Flux.just(4,5)).subscribe(System.out::println);


//        Flux flux1 = Flux.just(1,2);
//        Flux flux2 = Flux.just(3,4);
//        Flux.zip(flux1, flux2).subscribe(System.out::println);
//        Flux.just(1,2).zipWith(Flux.just(3,4)).subscribe(System.out::println);

//        Flux.just(1,2,3).any(e -> e % 2 == 0).subscribe(System.out::println);

//        Flux.concat(Flux.range(1,3), Flux.range(4,2), Flux.range(6,5)).subscribe(System.out::println);

        Flux.range(1, 10).reduce((x, y) -> {
            return x + y;
        }).subscribe(System.out::println);
        // 带初始值10，再累加21
        Flux.range(1, 10).reduceWith(() -> 10, (x, y) -> {
            return x + y;
        }).subscribe(System.out::println);
    }

    @Test
    public void test2() {
        Flux<Long> flux = createFlux();
        flux.subscribe();
    }

    public Flux<Long> createFlux() {
        return Flux.generate(sink -> {
            sink.next(System.currentTimeMillis());
        });
    }
}
