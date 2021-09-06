package dev.coly.jdat;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

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
            if (e.getName().equals("embed")) {
                e.replyEmbeds(getTestEmbed()).queue();
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
