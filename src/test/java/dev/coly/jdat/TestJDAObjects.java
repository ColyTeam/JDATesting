package dev.coly.jdat;

import dev.coly.jdat.entities.events.FakeGuildMessageReceivedEvent;
import dev.coly.jdat.entities.events.FakeSlashCommandEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

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

    @Test
    public void testFakeSlashCommandEvent() {
        FakeSlashCommandEvent event = JDAObjects.getFakeSlashCommandEvent("ping", 0, new HashMap<>());
        new TestEventListener().onEvent(event);
        try {
            Assertions.assertEquals(event.awaitReturn().getMessage().getContentRaw(), "Pong!");
        } catch (InterruptedException e) {
            Assertions.fail(e);
        }
    }

}
