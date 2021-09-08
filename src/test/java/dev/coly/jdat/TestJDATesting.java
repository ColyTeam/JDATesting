package dev.coly.jdat;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

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

    @Test
    public void testTestGuildMessageReceivedEventWithEmbeds() {
        try {
            Message message = JDATesting.testGuildMessageReceivedEvent(new TestEventListener(), ".embed");
            MessageEmbed embed = message.getEmbeds().get(0);
            Assertions.assertEquals("Test Embed", embed.getTitle());
            Assertions.assertEquals("Coly Team", Objects.requireNonNull(embed.getAuthor()).getName());
            Assertions.assertEquals("Test Name", embed.getFields().get(0).getName());
            Assertions.assertEquals("Test Value", embed.getFields().get(0).getValue());
            Assertions.assertTrue(embed.getFields().get(0).isInline());
        } catch (InterruptedException e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void testAssertGuildMessageReceivedEventWithEmbeds() {
        JDATesting.assertGuildMessageReceivedEvent(new TestEventListener(), ".embed",
                new ArrayList<>(Collections.singleton(TestEventListener.getTestEmbed())));
    }

    @Test
    public void testAssertSlashCommandEvent() {
        JDATesting.assertSlashCommandEvent(new TestEventListener(), "ping", new HashMap<>(), "Pong!");
    }

    @Test
    public void testAssertSlashCommandEventWithOptions() {
        Map<String, Object> map = new HashMap<>();
        map.put("bool", true);
        map.put("str", "text");
        map.put("number", 42);
        map.put("user", JDAObjects.getFakeUser());
        JDATesting.assertSlashCommandEvent(new TestEventListener(), "options", map,
                "bool: true - str: text - number: 42 - user: User#0000");
    }

    @Test
    public void testAssertSlashCommandEventWithEmbeds() {
        JDATesting.assertSlashCommandEvent(new TestEventListener(), "embed", new HashMap<>(),
                new ArrayList<>(Collections.singleton(TestEventListener.getTestEmbed())));
    }

}
