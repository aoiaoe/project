package com.geekbang.in_action.rate_limit.v1.algos;

import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;

/**
 * @author jzm
 * @date 2023/4/27 : 11:05
 */
public class TimeWindowRateLimitAlgo extends RateLimitAlgo {

    public TimeWindowRateLimitAlgo(int limit){
        this.limit = limit;
    }

    public TimeWindowRateLimitAlgo(int limit, int unit){
        this(limit);
        this.unit = unit;
        this.stopwatch = Stopwatch.createUnstarted();
        this.stopwatch.start();
    }

    protected TimeWindowRateLimitAlgo(int limit,  int unit, Stopwatch stopwatch) {
        this(limit, unit);
        this.stopwatch = stopwatch;
    }

    @Override
    public boolean tryAcquire() throws Exception {
        int count = currentCount.incrementAndGet();
        if (count <= limit) {
            return true;
        }
        try {
            if (lock.tryLock(TRY_LOCK_TIMEOUT, TimeUnit.MILLISECONDS)) {
                try {
                    // 如果超出时间窗口，则重置所有指标
                    if (stopwatch.elapsed(TimeUnit.MILLISECONDS) > TimeUnit.SECONDS.toMillis(1)) {
                        currentCount.set(0);
                        stopwatch.reset();
                        stopwatch.start();
                    }
                    count = currentCount.incrementAndGet();
                    return count <= limit;
                } finally {
                    lock.unlock();
                }
            } else {
                throw new RuntimeException("tryAcquire() wait lock too long");
            }
        } catch (Exception e) {
            throw new RuntimeException("tryAcquire() is interrupted by lock-time-up");
        }
    }

    public static void main(String[] args) {
        Stopwatch stopwatch = Stopwatch.createUnstarted();
        stopwatch.start();
        try {
            TimeUnit.MILLISECONDS.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
        try {
            TimeUnit.MILLISECONDS.sleep(1400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
        stopwatch.reset();
        stopwatch.start();
        try {
            TimeUnit.MILLISECONDS.sleep(1600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }
}
