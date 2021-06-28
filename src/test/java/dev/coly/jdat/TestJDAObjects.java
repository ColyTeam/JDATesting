package dev.coly.jdat;

import dev.coly.jdat.entities.events.FakeGuildMessageReceivedEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestJDAObjects {

    @Test
    public void testFakeGuildMessageReceivedEvent() {
        FakeGuildMessageReceivedEvent event = JDAObjects.getFakeGuildMessageReceivedEvent(".ping");
        new TestEventListener().onEvent(event);
        try {
            Assertions.assertEquals(event.awaitReturn().getContentRaw(), "Pong!");
        } catch (InterruptedException e) {
            e.printStackTrace();
            Assertions.fail(e);
        }
    }

}
