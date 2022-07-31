package dev.coly.jdat;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class TestEventListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent e) {
        if (e.getMessage().getContentDisplay().equals(".ping")) {
            e.getChannel().sendMessage("Pong!").queue();
        } else if (e.getMessage().getContentDisplay().equals(".embed")) {
            e.getChannel().sendMessageEmbeds(getTestEmbed()).queue();
        }
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {
        switch (e.getName()) {
            case "embed" -> e.replyEmbeds(getTestEmbed()).queue();
            case "ping" -> e.reply("Pong!").queue();
            case "options" -> e.reply("bool: " + Objects.requireNonNull(e.getOption("bool")).getAsBoolean() +
                    " - str: " + Objects.requireNonNull(e.getOption("str")).getAsString() +
                    " - number: " + Objects.requireNonNull(e.getOption("number")).getAsLong() +
                    " - user: " + Objects.requireNonNull(e.getOption("user")).getAsUser().getAsTag()).queue();
            case "defer" -> e.deferReply().queue();
            case "ephemeral" -> e.reply("Test").setEphemeral(true).queue();
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
