package dev.coly.jdat;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class TestEventListener implements EventListener {

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (event instanceof GuildMessageReceivedEvent) {
            GuildMessageReceivedEvent e = (GuildMessageReceivedEvent) event;
            if (e.getMessage().getContentDisplay().equals(".ping")) {
                e.getChannel().sendMessage("Pong!").queue();
            } else if (e.getMessage().getContentDisplay().equals(".embed")) {
                e.getChannel().sendMessage(getTestEmbed()).queue();
            }
        } else if (event instanceof SlashCommandEvent) {
            SlashCommandEvent e = (SlashCommandEvent) event;
            switch (e.getName()) {
                case "embed":
                    e.replyEmbeds(getTestEmbed()).queue();
                    break;
                case "ping":
                    e.reply("Pong!").queue();
                    break;
                case "options":
                    e.reply("bool: " + Objects.requireNonNull(e.getOption("bool")).getAsBoolean() +
                            " - str: " + Objects.requireNonNull(e.getOption("str")).getAsString() +
                            " - number: " + Objects.requireNonNull(e.getOption("number")).getAsLong() +
                            " - user: " + Objects.requireNonNull(e.getOption("user")).getAsUser().getAsTag()).queue();
            }
        }
    }

    public static MessageEmbed getTestEmbed() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Test Embed");
        embedBuilder.setAuthor("Coly Team");
        embedBuilder.addField("Test Name", "Test Value", true);
        return embedBuilder.build();
    }

}
