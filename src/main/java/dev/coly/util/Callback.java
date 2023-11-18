package dev.coly.util;

import java.util.concurrent.TimeUnit;

public interface Callback<T> {

    static <T> Callback<T> single() {
        return new SingleCallback<>();
    }

    static <T> Callback<T> multiple() {
        return new MultipleCallback<>();
    }

    static <T> Callback<T> multiple(int count) {
        return new MultipleCallback<>(count);
    }

    void callback(T object);

    T await() throws InterruptedException;

    T await(long timeout, TimeUnit timeUnit) throws InterruptedException;
}
