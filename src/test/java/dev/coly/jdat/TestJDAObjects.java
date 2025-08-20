package dev.coly.jdat;

import dev.coly.util.Callback;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.unions.GuildChannelUnion;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TestJDAObjects {

    @Test
    public void testGetJDA() {
        JDA jda = JDAObjects.getJDA();
        Assertions.assertEquals(0L, jda.getGatewayPing());
        Assertions.assertTrue(jda.unloadUser(0L));
        Assertions.assertEquals("Test", jda.getSelfUser().getName());
        Assertions.assertEquals("0000", jda.getSelfUser().getDiscriminator());
        Assertions.assertEquals("Test#0000", jda.getSelfUser().getAsTag());
        Assertions.assertEquals("http://fake.url", jda.getInviteUrl());
        Assertions.assertEquals("http://fake.url", jda.getInviteUrl(Permission.ADMINISTRATOR));
        Assertions.assertEquals("http://fake.url",
                jda.getInviteUrl(Permission.ADMINISTRATOR, Permission.MANAGE_PERMISSIONS));
        Assertions.assertEquals("http://fake.url", jda.getInviteUrl(List.of(Permission.ADMINISTRATOR)));
    }

    @Test
    public void testGetSlashCommandInteractionEventOptions() {
        Map<String, Object> map = new HashMap<>();
        map.put("string", "penis");
        map.put("boolean", true);
        map.put("long", 1L);
        map.put("int", 1);
        map.put("double", 1d);
        SlashCommandInteractionEvent event = JDAObjects.getSlashCommandInteractionEvent(null, "command",
                "subcommand", "subcommandGroup", map, Callback.single());
        Assertions.assertEquals("penis", Objects.requireNonNull(event.getOption("string")).getAsString());
        Assertions.assertTrue(Objects.requireNonNull(event.getOption("boolean")).getAsBoolean());
        Assertions.assertEquals(1L, Objects.requireNonNull(event.getOption("long")).getAsLong());
        Assertions.assertEquals(1, Objects.requireNonNull(event.getOption("int")).getAsInt());
        Assertions.assertEquals(1d, Objects.requireNonNull(event.getOption("double")).getAsDouble());
    }

    @Test
    public void testGetGuildChannelUnion() {
        GuildChannelUnion guildChannelUnion = JDAObjects.getGuildChannelUnion("test-channel", 42,
                Callback.single());
        Assertions.assertEquals("test-channel", guildChannelUnion.getName());
        Assertions.assertEquals("42", guildChannelUnion.getId());
        Assertions.assertEquals(42, guildChannelUnion.getIdLong());
        Assertions.assertNotNull(guildChannelUnion.asTextChannel());
        Assertions.assertNotNull(guildChannelUnion.asGuildMessageChannel());
        Assertions.assertNotNull(guildChannelUnion.asNewsChannel());
    }

    @Test
    public void testGetGuildJoinEvent() {
        GuildJoinEvent event = JDAObjects.getGuildJoinEvent(JDAObjects.getGuild("Test Guild"));
        Assertions.assertNotNull(event);
        Assertions.assertEquals("Test Guild", event.getGuild().getName());
    }

    @Test
    public void testGetGuild() {
        Guild guild = JDAObjects.getGuild("Test Guild");
        Assertions.assertNotNull(guild);
        Assertions.assertEquals("Test Guild", guild.getName());
    }

}
