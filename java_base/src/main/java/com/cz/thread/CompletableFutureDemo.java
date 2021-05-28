package com.cz.thread;


import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.concurrent.TimeUnit.SECONDS;

public class CompletableFutureDemo {

    public static void main(String[] args) {
        long s = System.currentTimeMillis();
        System.out.println("start:" + s);
        Stream.<Runnable>of(
                () -> {
                    try{SECONDS.sleep(1);}catch(Exception e){e.printStackTrace();}
                    System.out.println(1);
                },
                () -> {
                    System.out.println(2);
                    try{SECONDS.sleep(2);}catch(Exception e){e.printStackTrace();}
                },
                () -> {
                    System.out.println(3);
                    try{SECONDS.sleep(3);}catch(Exception e){e.printStackTrace();}
                },
                () -> {
                    System.out.println(4);
                    try{SECONDS.sleep(4);}catch(Exception e){e.printStackTrace();}
                }
        ).map(r -> CompletableFuture.runAsync(r))
                .collect(Collectors.toList());//.stream().map(CompletableFuture::join).collect(Collectors.toList());
        long e = System.currentTimeMillis();
        System.out.println("end:" + e);
        System.out.println(e - s);
    }
}
