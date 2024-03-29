package dev.coly.jdat;

import dev.coly.util.Callback;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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
        map.put("number", 42L);
        map.put("user", JDAObjects.getMember("User", "0000").getUser());
        JDATesting.assertSlashCommandEvent(new TestEventListener(), "options", map,
                "bool: true - str: text - number: 42 - user: User#0000");
    }

    @Test
    public void testAssertSlashCommandEventWithEmbeds() {
        JDATesting.assertSlashCommandEvent(new TestEventListener(), "embed", new HashMap<>(),
                new ArrayList<>(Collections.singleton(TestEventListener.getTestEmbed())));
    }

    @Test
    public void testGetSlashCommandInteractionEvent() {
        Map<String, Object> options = new HashMap<>();
        options.put("test", true);
        SlashCommandInteractionEvent event = JDAObjects.getSlashCommandInteractionEvent(
                JDAObjects.getMessageChannel("test-channel", 1L, Callback.single()), "slash-command",
                "subcommand", "subcommand-group", options, Callback.single());
        Assertions.assertNotNull(event.getChannel());
        Assertions.assertEquals("test-channel", event.getChannel().getName());
        Assertions.assertEquals("slash-command", event.getName());
        Assertions.assertEquals("subcommand", event.getSubcommandName());
        Assertions.assertEquals("subcommand-group", event.getSubcommandGroup());
        Assertions.assertNotNull(event.getOption("test"));
        Assertions.assertTrue(Objects.requireNonNull(event.getOption("test")).getAsBoolean());
    }

    @Test
    public void testSlashCommandWithDeferReply() {
        Map<String, Object> options = new HashMap<>();
        options.put("test", true);
        Callback<Boolean> callback = Callback.single();
        SlashCommandInteractionEvent event = JDAObjects.getSlashCommandInteractionEvent(
                JDAObjects.getMessageChannel("test-channel", 1L, Callback.single()), "defer",
                "subcommand", "subcommand-group", options, Callback.single(), callback);
        new TestEventListener().onEvent(event);
        try {
            Assertions.assertFalse(callback.await(100L, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void testSlashCommandWithEphemeral() {
        Callback<Message> callback = Callback.single();
        MessageChannel messageChannel = JDAObjects.getMessageChannel("channel", 1L, callback);
        SlashCommandInteractionEvent event = JDAObjects.getSlashCommandInteractionEvent(messageChannel, "ephemeral",
                null, null, null, callback);
        new TestEventListener().onEvent(event);
        try {
            Message message = callback.await(100L, TimeUnit.MILLISECONDS);
            Assertions.assertTrue(message.isEphemeral());
        } catch (InterruptedException e) {
            Assertions.fail(e);
        }
    }

}
