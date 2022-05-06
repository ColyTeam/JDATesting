package dev.coly.jdat;

import dev.coly.util.Callback;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TestJDAObjects {

    @Test
    public void testGetJDA() {
        JDA jda = JDAObjects.getJDA();
        Assertions.assertEquals(0L, jda.getGatewayPing());
        Assertions.assertTrue(jda.unloadUser(0L));
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
                "subcommand", "subcommandGroup", map, new Callback<>());
        Assertions.assertEquals("penis", Objects.requireNonNull(event.getOption("string")).getAsString());
        Assertions.assertTrue(Objects.requireNonNull(event.getOption("boolean")).getAsBoolean());
        Assertions.assertEquals(1L, Objects.requireNonNull(event.getOption("long")).getAsLong());
        Assertions.assertEquals(1, Objects.requireNonNull(event.getOption("int")).getAsInt());
        Assertions.assertEquals(1d, Objects.requireNonNull(event.getOption("double")).getAsDouble());
    }

}
