package dev.coly.jdat;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TestSlashCommands {

    @Test
    public void testAssertSlashCommandEvent() {
        JDATesting.assertSlashCommandEvent(new TestEventListener(), "ping", null, null, new HashMap<>(), "Pong!");
    }

    @Test
    public void testAssertSlashCommandEventWithOptions() {
        Map<String, Object> map = new HashMap<>();
        map.put("bool", true);
        map.put("str", "text");
        map.put("number", 42);
        map.put("user", JDAObjects.getMember("User", "0000").getUser());
        JDATesting.assertSlashCommandEvent(new TestEventListener(), "options", map,
                "bool: true - str: text - number: 42 - user: User#0000");
    }

    @Test
    public void testAssertSlashCommandEventWithEmbeds() {
        JDATesting.assertSlashCommandEvent(new TestEventListener(), "embed", new HashMap<>(),
                new ArrayList<>(Collections.singleton(TestEventListener.getTestEmbed())));
    }

    // TODO: fix defer reply
    /*
    @Test
    public void testSlashCommandWithDeferReply() {
        SlashCommandInteractionEvent event = JDAObjects.getSlashCommandInteractionEvent("defer", 0, new HashMap<>());
        new TestEventListener().onEvent(event);
        try {
            FakeReplyAction action = event.awaitReturn();
            Assertions.assertTrue(action.isDefer());
        } catch (InterruptedException e) {
            Assertions.fail(e);
        }
    }
    */

}
