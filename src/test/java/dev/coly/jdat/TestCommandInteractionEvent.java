package dev.coly.jdat;

import dev.coly.util.Callback;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.context.MessageContextInteraction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class TestCommandInteractionEvent {

    @Test
    public void testTestMessageContextInteractionEvent() {
        try {
            Message message = JDATesting.testMessageContextInteractionEvent(new TestEventListener(), "ping", null);
            Assertions.assertNotNull(message);
            Assertions.assertEquals("Pong!", message.getContentRaw());
        } catch (InterruptedException e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void testAssertMessageContextInteractionEvent() {
        JDATesting.assertMessageContextInteractionEvent(new TestEventListener(), "ping", null, "Pong!");
    }

    @Test
    public void testAssertMessageContextInteractionEventWithOptions() {
        Map<String, Object> map = new HashMap<>();
        map.put("bool", true);
        map.put("str", "text");
        map.put("number", 42L);
        map.put("user", JDAObjects.getMember("User", "0000").getUser());
        JDATesting.assertMessageContextInteractionEvent(new TestEventListener(), "options", map,
                "bool: true - str: text - number: 42 - user: User#0000");
    }

    @Test
    public void testAssertMessageContextInteractionEventWithEmbeds() {
        JDATesting.assertMessageContextInteractionEvent(new TestEventListener(), "embed", new HashMap<>(),
                new ArrayList<>(Collections.singleton(TestEventListener.getTestEmbed())));
    }

    @Test
    public void testGetMessageContextInteractionEvent() {
        Map<String, Object> options = new HashMap<>();
        options.put("test", true);
        MessageContextInteraction interaction = getMessageContextInteraction("slash-command", options,
                Callback.single(), Callback.single());
        MessageContextInteractionEvent event = JDAObjects.getMessageContextInteractionEvent(interaction);
        Assertions.assertNotNull(event.getChannel());
        Assertions.assertEquals("test-channel", event.getChannel().getName());
        Assertions.assertEquals("slash-command", event.getName());
        Assertions.assertNotNull(event.getOption("test"));
        Assertions.assertTrue(Objects.requireNonNull(event.getOption("test")).getAsBoolean());
    }

    @Test
    public void testMessageContextInteractionWithDeferReply() {
        Map<String, Object> options = new HashMap<>();
        options.put("test", true);
        Callback<Boolean> callback = Callback.single();
        MessageContextInteraction interaction = getMessageContextInteraction("defer", options,
                Callback.single(), callback);
        MessageContextInteractionEvent event = JDAObjects.getMessageContextInteractionEvent(interaction);
        new TestEventListener().onEvent(event);
        try {
            Assertions.assertFalse(callback.await(100L, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void testMessageContextInteractionWithEphemeral() {
        Callback<Message> callback = Callback.single();
        MessageContextInteraction interaction = getMessageContextInteraction("ephemeral", null,
                callback, Callback.single());
        MessageContextInteractionEvent event = JDAObjects.getMessageContextInteractionEvent(interaction);
        new TestEventListener().onEvent(event);
        try {
            Message message = callback.await(100L, TimeUnit.MILLISECONDS);
            Assertions.assertTrue(message.isEphemeral());
        } catch (InterruptedException e) {
            Assertions.fail(e);
        }
    }

    private MessageContextInteraction getMessageContextInteraction(String command, Map<String, Object> options,
                                                                   Callback<Message> messageCallback,
                                                                   Callback<Boolean> deferCallback) {
        MessageChannel channel = JDAObjects.getMessageChannel("test-channel", 1L, Callback.single());
        Member member = JDAObjects.getMember("User", "0000");
        return JDAObjects.getMessageContextInteraction(channel, member, command, options, messageCallback,
                deferCallback);
    }

}
