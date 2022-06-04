package dev.coly.jdat;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TestMessageReceivedEvent {

    @Test
    public void testTestMessageReceivedEvent() {
        try {
            Assertions.assertEquals("Pong!",
                    JDATesting.testMessageReceivedEvent(new TestEventListener(), ".ping").getContentRaw());
        } catch (InterruptedException e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void testAssertMessageReceivedEvent() {
        JDATesting.assertGuildMessageReceivedEvent(new TestEventListener(), ".ping", "Pong!");
    }

    @Test
    public void testTestMessageReceivedEventWithEmbeds() {
        try {
            Message message = JDATesting.testMessageReceivedEvent(new TestEventListener(), ".embed");
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
    public void testAssertMessageReceivedEventWithEmbeds() {
        JDATesting.assertGuildMessageReceivedEvent(new TestEventListener(), ".embed",
                new ArrayList<>(Collections.singleton(TestEventListener.getTestEmbed())));
    }

}
