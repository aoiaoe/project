package com.jzm.common.executor;

public interface CallableTask<T> {

    T execute();

    void success(T t);

    void fail(Throwable throwable);

}
