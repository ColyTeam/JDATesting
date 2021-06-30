package dev.coly.jdat;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Test Embed");
        embedBuilder.setAuthor("Coly Team");
        embedBuilder.addField("Test Name", "Test Value", true);

        JDATesting.assertGuildMessageReceivedEvent(new TestEventListener(), ".embed",
                new ArrayList<>(Collections.singleton(embedBuilder.build())));
    }

}
