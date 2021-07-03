package com.cz.thread;

import java.util.concurrent.*;

/**
 * @author alian
 * @date 2021/1/22 上午 11:56
 * @since JDK8
 */
public class ThreadPoolDemo {

    public static void main(String[] args) {
        Callable callable = new Callable() {
            @Override
            public Object call() throws Exception {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 1024;
            }
        };

        FutureTask futureTask = new FutureTask<>(callable);
        final Thread thread = new Thread(futureTask);
        thread.start();
        try {
            System.out.println(System.currentTimeMillis());
            final Object o = futureTask.get();
            System.out.println(o);
            System.out.println(System.currentTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void callableWithExecutors() {
        ExecutorService executorService = new ThreadPoolExecutor(10,
                10,
                10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(100));

        Callable callable = new Callable() {
            @Override
            public Object call() throws Exception {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 1024;
            }
        };

        final Future future = executorService.submit(callable);
        try {
            System.out.println(System.currentTimeMillis());
            final Object o = future.get();
            System.out.println(o);
            System.out.println(System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }

}
