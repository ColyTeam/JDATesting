package dev.coly.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class MultipleCallback<T> implements Callback<T> {
    private final BlockingQueue<T> queue;

    public MultipleCallback() {
        this(Integer.MAX_VALUE);
    }

    public MultipleCallback(int count) {
        this.queue = new LinkedBlockingQueue<>(count);
    }

    @Override
    public void callback(T object) {
        queue.add(object);
    }

    @Override
    public T await() throws InterruptedException {
        return queue.take();
    }

    @Override
    public T await(long timeout, TimeUnit timeUnit) throws InterruptedException {
        return queue.poll(timeout, timeUnit);
    }
}
