package dev.coly.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class TestCallback {

    @Test
    public void testCallback() {
        Callback<String> callback = new Callback<>();
        methodCallback(callback);
        try {
            Assertions.assertEquals("test", callback.await());
        } catch (InterruptedException e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void testCallbackTimeout() {
        Callback<String> callback = new Callback<>();
        methodCallback(callback);
        try {
            Assertions.assertEquals("test", callback.await(1000L, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void testCallbackTimeoutFailed() {
        Assertions.assertThrows(InterruptedException.class,
                () -> new Callback<>().await(100L, TimeUnit.MILLISECONDS));
    }

    private void methodCallback(Callback<String> callback) {
        callback.callback("test");
    }

}
