package dev.coly.jdat;

import dev.coly.jdat.entities.*;
import dev.coly.jdat.entities.events.FakeGuildMessageReceivedEvent;
import dev.coly.jdat.entities.events.FakeSlashCommandEvent;
import net.dv8tion.jda.api.entities.AbstractChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.utils.data.DataArray;
import net.dv8tion.jda.api.utils.data.DataObject;
import net.dv8tion.jda.internal.JDAImpl;
import net.dv8tion.jda.internal.interactions.CommandInteractionImpl;
import net.dv8tion.jda.internal.utils.config.AuthorizationConfig;

import java.util.*;

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
     * Returns a fake {@link net.dv8tion.jda.api.events.interaction.SlashCommandEvent}.
     *
     * @param command       The command that would be executed by a {@link User}.
     * @param commandId     The id of the command that would be executed by a {@link User}.
     * @param options       The options that the command would have.
     * @return              a fake {@link net.dv8tion.jda.api.events.interaction.SlashCommandEvent}.
     */
    public static FakeSlashCommandEvent getFakeSlashCommandEvent(String command, long commandId, Map<String, Object> options) {
        return new FakeSlashCommandEvent(getFakeJDA(), getFakeCommandInteraction(command, commandId, options));
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
    public static FakeMessage getFakeMessage(Collection<? extends MessageEmbed> messageEmbeds) {
        return new FakeMessage(getFakeTextChannel(), new ArrayList<>(messageEmbeds));
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

    /**
     * Returns a fake {@link net.dv8tion.jda.api.entities.User}.
     *
     * @return  A fake {@link net.dv8tion.jda.api.entities.User}.
     */
    public static FakeUser getFakeUser() {
        return new FakeUser();
    }

    /**
     * Returns a fake {@link CommandInteractionImpl}. This can be only used as a slash command.
     *
     * @param command       The command that would be executed by a {@link User}.
     * @param commandId     The id of the command that would be executed by a {@link User}.
     * @param valueMapping  The options that the command would have.
     * @return              a fake {@link CommandInteractionImpl}.
     */
    public static CommandInteractionImpl getFakeCommandInteraction(String command, long commandId, Map<String, Object> valueMapping) {
        DataObject dataObject = DataObject.empty();
        dataObject.put("id", "0");
        dataObject.put("token", "0");
        dataObject.put("guild_id", 0);
        dataObject.put("type", 1);
        dataObject.put("channel_id", 0);
        dataObject.put("user", DataObject.empty().put("id", "0").put("username", "User").put("discriminator", "0000"));

        DataObject data = DataObject.empty();
        data.put("id", commandId);
        data.put("name", command);

        List<DataObject> options = new LinkedList<>();
        for (Map.Entry<String, Object> entry : valueMapping.entrySet()) {
            DataObject option = DataObject.empty();
            Object value = entry.getValue();

            if (entry.getValue() instanceof String) {
                option.put("type", 3);
            } else if (value instanceof Long | entry.getValue() instanceof Integer) {
                option.put("type", 4);
            } else if (value instanceof Boolean) {
                option.put("type", 5);
            } else if (value instanceof User) {
                option.put("type", 6);
                value = ((User) value).getId();
            } else if (value instanceof AbstractChannel) {
                option.put("type", 7);
                value = ((AbstractChannel) value).getId();
            } else if (value instanceof Role) {
                option.put("type", 8);
                value = ((Role) value).getId();
            } else if (value instanceof Double) {
                option.put("type", 10);
            } else {
                option.put("type", 0);
            }

            option.put("name", entry.getKey());
            option.put("value", value);
            options.add(option);
        }
        data.put("options", DataArray.empty().addAll(options));

        dataObject.put("data", data);

        return new CommandInteractionImpl(getFakeJDAImpl(), dataObject);
    }

    /**
     * Returns a fake {@link JDAImpl}. This only contains a {@link AuthorizationConfig} with the token "0".
     *
     * @return  a fake {@link JDAImpl}.
     */
    public static JDAImpl getFakeJDAImpl() {
        return new FakeJDAImpl(new AuthorizationConfig("0"));
    }

    /**
     * Returns a fake {@link net.dv8tion.jda.api.entities.SelfUser}.
     *
     * @return  a fake {@link net.dv8tion.jda.api.entities.SelfUser}.
     */
    public static FakeSelfUser getFakeSelfUser() {
        return new FakeSelfUser();
    }

}
