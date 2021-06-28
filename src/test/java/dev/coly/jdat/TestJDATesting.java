package dev.coly.jdat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestJDATesting {

    @Test
    public void testTestGuildMessageReceivedEvent() {
        try {
            Assertions.assertEquals("Pong!",
                    JDATesting.testGuildMessageReceivedEvent(new TestEventListener(), ".ping").getContentRaw());
        } catch (InterruptedException e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void testAssertGuildMessageReceivedEvent() {
        JDATesting.assertGuildMessageReceivedEvent(new TestEventListener(), ".ping", "Pong!");
    }

}
