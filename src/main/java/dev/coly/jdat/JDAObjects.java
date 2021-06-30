package dev.coly.jdat;

import dev.coly.jdat.entities.FakeGuild;
import dev.coly.jdat.entities.FakeJDA;
import dev.coly.jdat.entities.FakeMessage;
import dev.coly.jdat.entities.FakeTextChannel;
import dev.coly.jdat.entities.events.FakeGuildMessageReceivedEvent;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.List;

/**
 * <p>Fake objects from JDA objects.</p>
 * <br>
 * <p><strong>IMPORTANT</strong></p>
 * <p>Not all methods are implemented in the fake JDA objects.</p>
 *
 */
public class JDAObjects {

    /**
     * Returns a fake {@link net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent}.
     *
     * @param message   The message that would trigger this event.
     * @return          A fake {@link net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent}.
     */
    public static FakeGuildMessageReceivedEvent getFakeGuildMessageReceivedEvent(FakeMessage message) {
        return new FakeGuildMessageReceivedEvent(getFakeJDA(), message);
    }

    /**
     * Returns a fake {@link net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent}.
     *
     * @param contentRaw    The raw content of a message that would trigger this event.
     * @return              A fake {@link net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent}.
     */
    public static FakeGuildMessageReceivedEvent getFakeGuildMessageReceivedEvent(String contentRaw) {
        return new FakeGuildMessageReceivedEvent(getFakeJDA(), getFakeMessage(contentRaw));
    }

    /**
     * Return a fake {@link net.dv8tion.jda.api.JDA}.
     *
     * @return  A fake {@link net.dv8tion.jda.api.JDA}.
     */
    public static FakeJDA getFakeJDA() {
        return new FakeJDA();
    }

    /**
     * Returns a fake {@link net.dv8tion.jda.api.entities.Message}.
     *
     * @param channel       The {@link FakeTextChannel} the message would be send in.
     * @param contentRaw    The raw content of the {@link net.dv8tion.jda.api.entities.Message},
     * @return              A fake {@link net.dv8tion.jda.api.entities.Message}.
     */
    public static FakeMessage getFakeMessage(FakeTextChannel channel, String contentRaw) {
        return new FakeMessage(channel, contentRaw);
    }

    /**
     * Returns a fake {@link net.dv8tion.jda.api.entities.Message}. It will use a {@link FakeTextChannel} from
     * {@link JDAObjects#getFakeTextChannel()}.
     *
     * @param contentRaw    The raw content of the {@link net.dv8tion.jda.api.entities.Message},
     * @return              A fake {@link net.dv8tion.jda.api.entities.Message}.
     */
    public static FakeMessage getFakeMessage(String contentRaw) {
        return new FakeMessage(getFakeTextChannel(), contentRaw);
    }

    /**
     * Returns a fake {@link net.dv8tion.jda.api.entities.Message}.
     *
     * @param channel       The {@link FakeTextChannel} the message would be send in.
     * @param messageEmbeds  A list of {@link MessageEmbed} that would be send.
     * @return              A fake {@link net.dv8tion.jda.api.entities.Message}.
     */
    public static FakeMessage getFakeMessage(FakeTextChannel channel, List<MessageEmbed> messageEmbeds) {
        return new FakeMessage(channel, messageEmbeds);
    }

    /**
     * Returns a fake {@link net.dv8tion.jda.api.entities.Message}. It will use a {@link FakeTextChannel} from
     * {@link JDAObjects#getFakeTextChannel()}.
     *
     * @param messageEmbeds  A list of {@link MessageEmbed} that would be send.
     * @return              A fake {@link net.dv8tion.jda.api.entities.Message}.
     */
    public static FakeMessage getFakeMessage(List<MessageEmbed> messageEmbeds) {
        return new FakeMessage(getFakeTextChannel(), messageEmbeds);
    }

    /**
     * Returns a fake {@link net.dv8tion.jda.api.entities.TextChannel}.
     *
     * @return  A fake {@link net.dv8tion.jda.api.entities.TextChannel}.
     */
    public static FakeTextChannel getFakeTextChannel() {
        return new FakeTextChannel(getFakeGuild(), "fake-text-channel");
    }

    /**
     * Returns a fake {@link net.dv8tion.jda.api.entities.Guild}.
     *
     * @return  A fake {@link net.dv8tion.jda.api.entities.Guild}.
     */
    public static FakeGuild getFakeGuild() {
        return new FakeGuild(getFakeJDA(), "Fake Guild");
    }

}
