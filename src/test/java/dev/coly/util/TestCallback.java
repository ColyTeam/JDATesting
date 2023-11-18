package dev.coly.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class TestCallback {

    @ParameterizedTest
    @MethodSource("callbackInstances")
    public void testCallback() {
        Callback<String> callback = Callback.single();
        methodCallback(callback);
        try {
            Assertions.assertEquals("test", callback.await());
        } catch (InterruptedException e) {
            Assertions.fail(e);
        }
    }

    @ParameterizedTest
    @MethodSource("callbackInstances")
    public void testCallbackTimeout() {
        Callback<String> callback = Callback.single();
        methodCallback(callback);
        try {
            Assertions.assertEquals("test", callback.await(1000L, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            Assertions.fail(e);
        }
    }

    @ParameterizedTest
    @MethodSource("callbackInstances")
    public void testCallbackTimeoutFailed() {
        Assertions.assertThrows(InterruptedException.class,
                () -> Callback.single().await(100L, TimeUnit.MILLISECONDS));
    }

    @Test
    public void testMultipleCallback() {
        Callback<String> callback = Callback.multiple();
        methodCallback(callback);
        try {
            Assertions.assertEquals("test", callback.await());
        } catch (InterruptedException e) {
            Assertions.fail(e);
        }

        methodCallback(callback);
        callback.callback("test2");
        try {
            Assertions.assertEquals("test", callback.await());
            Assertions.assertEquals("test2", callback.await());
        } catch (InterruptedException e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void testMultipleCallbackLimit() {
        Callback<String> callback = Callback.multiple(2);
        methodCallback(callback);
        methodCallback(callback);
        Assertions.assertThrowsExactly(IllegalStateException.class, ()-> methodCallback(callback));
    }

    private static void methodCallback(Callback<String> callback) {
        callback.callback("test");
    }

    private static Stream<Callback> callbackInstances() {
        return Stream.of(
                Callback.single(),
                Callback.multiple(),
                Callback.multiple(5)
        );
    }

}
