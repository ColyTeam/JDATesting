package dev.coly.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

    private void methodCallback(Callback<String> callback) {
        callback.callback("test");
    }

}
