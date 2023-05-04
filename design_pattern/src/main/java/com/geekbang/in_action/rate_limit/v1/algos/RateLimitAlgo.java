package com.geekbang.in_action.rate_limit.v1.algos;

import com.google.common.base.Stopwatch;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author jzm
 * @date 2023/4/27 : 10:34
 */
public abstract class RateLimitAlgo  {

    /* timeout for {@code Lock.tryLock() }. */
    protected static final long TRY_LOCK_TIMEOUT = 200L; // 200ms.
    protected Stopwatch stopwatch;
    protected AtomicInteger currentCount = new AtomicInteger(0);
    protected int limit;
    protected int unit;

    protected Lock lock = new ReentrantLock();

    public abstract boolean tryAcquire() throws Exception;
}
