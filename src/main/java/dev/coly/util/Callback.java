package dev.coly.util;

import java.util.concurrent.CountDownLatch;

public class Callback<T> {

    private final CountDownLatch countDownLatch;
    private T object;

    public Callback() {
        this.countDownLatch = new CountDownLatch(1);
    }

    public void callback(T object) {
        this.object = object;
        this.countDownLatch.countDown();
    }

    public T await() throws InterruptedException {
        this.countDownLatch.await();
        return object;
    }

}
