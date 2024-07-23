package dev.coly.jdat;

import dev.coly.util.Callback;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.channel.concrete.NewsChannel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.unions.GuildChannelUnion;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import net.dv8tion.jda.api.interactions.commands.CommandInteraction;
import net.dv8tion.jda.api.interactions.commands.CommandInteractionPayload;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.context.MessageContextInteraction;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import net.dv8tion.jda.internal.JDAImpl;
import org.jetbrains.annotations.NotNull;
import org.mockito.Mockito;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

/**
 *
 */
public class JDAObjects {

    /**
     * Get a mocked {@link SlashCommandInteractionEvent}.
     *
     * @param channel           the channel this event would be executed.
     * @param name              the name of the slash command.
     * @param subcommandName    the name of the subcommand of the slash command.
     * @param subcommandGroup   the subcommand group of the slash command.
     * @param options           a map with all options for the slash command a user would have inputted.
     * @param messageCallback   a callback to receive messages that would be sent back to the channel with.
     *                          {@link MessageChannel#sendMessage(CharSequence)} or
     *                          {@link MessageChannel#sendMessageEmbeds(Collection)} for example.
     * @return                  a mocked {@link SlashCommandInteractionEvent}.
     */
    public static SlashCommandInteractionEvent getSlashCommandInteractionEvent(MessageChannel channel, String name,
                                                                               String subcommandName,
                                                                               String subcommandGroup,
                                                                               Map<String, Object> options,
                                                                               Callback<Message> messageCallback) {
        return getSlashCommandInteractionEvent(channel, name, subcommandName, subcommandGroup, options, messageCallback,
                Callback.single());
    }

    /**
     * Get a mocked {@link SlashCommandInteractionEvent}.
     *
     * @param channel           the channel this event would be executed.
     * @param name              the name of the slash command.
     * @param subcommandName    the name of the subcommand of the slash command.
     * @param subcommandGroup   the subcommand group of the slash command.
     * @param options           a map with all options for the slash command a user would have inputted.
     * @param messageCallback   a callback to receive messages that would be sent back to the channel with.
     * @param deferReply        a callback that is called when a deferred reply is called. The boolean is true when
     *                          the message is ephemeral and false if not.
     *                          {@link MessageChannel#sendMessage(CharSequence)} or
     *                          {@link MessageChannel#sendMessageEmbeds(Collection)} for example.
     * @return                  a mocked {@link SlashCommandInteractionEvent}.
     */
    public static SlashCommandInteractionEvent getSlashCommandInteractionEvent(MessageChannel channel, String name,
                                                                               String subcommandName,
                                                                               String subcommandGroup,
                                                                               Map<String, Object> options,
                                                                               Callback<Message> messageCallback,
                                                                               Callback<Boolean> deferReply) {
        Member member = getMember("User", "0000");
        CommandInteraction interaction = getCommandInteraction(channel, member, name, subcommandName, subcommandGroup,
                options, CommandInteraction.class);
        SlashCommandInteractionEvent event = getCommandInteractionEvent(interaction, messageCallback, deferReply,
                SlashCommandInteractionEvent.class);

        mockCallbacks(event, channel, messageCallback, deferReply);

        return event;
    }

    /**
     * Get a mocked {@link ReplyCallbackAction}.
     *
     * @param message           the message that this reply should produce.
     * @param messageCallback   the callback for receiving the message.
     * @return                  a mocked {@link ReplyCallbackAction}.
     */
    private static ReplyCallbackAction getReplyCallbackAction(Message message, Callback<Message> messageCallback) {
        ReplyCallbackAction action = mock(ReplyCallbackAction.class);

        Mockito.doAnswer(invocation -> {
            messageCallback.callback(message);
            return null;
        }).when(action).queue();

        when(action.setEphemeral(anyBoolean())).thenAnswer(invocation -> {
           when(message.isEphemeral()).thenReturn(true);
           return action;
        });
        return action;
    }

    /**
     * Get a mocked {@link MessageReceivedEvent}.
     *
     * @param channel   the channel the message would be sent in.
     * @param message   the message that would be sent.
     * @param member    the member that would send this message.
     * @return          a mocked {@link MessageReceivedEvent}.
     */
    public static MessageReceivedEvent getMessageReceivedEvent(MessageChannel channel, Message message, Member member) {
        MessageReceivedEvent event = mock(MessageReceivedEvent.class);
        when(event.getChannel()).thenAnswer(invocation -> channel);
        when(event.getMessage()).thenAnswer(invocation -> message);
        when(event.getMember()).thenAnswer(invocation -> member);
        when(event.getAuthor()).thenAnswer(invocation -> member.getUser());
        return event;
    }

    /**
     * Get a mocked {@link MessageContextInteractionEvent}.
     *
     * @param interaction   the interaction the triggered this event.
     * @return              a mocked {@link MessageContextInteractionEvent}.
     */
    public static MessageContextInteractionEvent getMessageContextInteractionEvent(
            MessageContextInteraction interaction, Callback<Message> messageCallback, Callback<Boolean> deferReply) {
        return getCommandInteractionEvent(interaction, messageCallback, deferReply, MessageContextInteractionEvent.class);
    }

    protected static <T extends GenericCommandInteractionEvent> T getCommandInteractionEvent(
            CommandInteraction interaction, Callback<Message> messageCallback, Callback<Boolean> deferReply,
            Class<T> clazz) {
        T event = mock(clazz);
        JDA jda = getJDA();

        when(event.getChannel()).thenAnswer(invocation -> interaction.getChannel());
        when(event.getJDA()).thenReturn(jda);
        when(event.getInteraction()).thenAnswer(invocation -> interaction);
        when(event.getChannelId()).thenAnswer(invocation -> interaction.getChannelId());
        when(event.getChannelIdLong()).thenAnswer(invocation -> interaction.getChannelIdLong());
        when(event.getChannelType()).thenAnswer(invocation -> interaction.getChannelType());
        when(event.getMessageChannel()).thenAnswer(invocation -> interaction.getMessageChannel());
        when(event.getGuildChannel()).thenAnswer(invocation -> interaction.getGuildChannel());
        when(event.getMember()).thenAnswer(invocation -> interaction.getMember());
        when(event.getFullCommandName()).thenAnswer(invocation -> interaction.getFullCommandName());
        when(event.getName()).thenAnswer(invocation -> interaction.getName());
        when(event.getSubcommandName()).thenAnswer(invocation -> interaction.getSubcommandName());
        when(event.getSubcommandGroup()).thenAnswer(invocation -> interaction.getSubcommandGroup());
        when(event.getOptions()).thenAnswer(invocation -> interaction.getOptions());
        when(event.getOption(anyString())).thenAnswer(invocation ->
                interaction.getOption(invocation.getArgument(0)));
        when(event.getOptionsByName(anyString())).thenAnswer(invocation ->
                interaction.getOptionsByName(invocation.getArgument(0)));
        when(event.getOptionsByType(any(OptionType.class))).thenAnswer(invocation ->
                interaction.getOptionsByType(invocation.getArgument(0)));

        mockCallbacks(event, (MessageChannel) event.getChannel(), messageCallback, deferReply);

        return event;
    }

    /**
     * Get a mocked {@link GuildJoinEvent}.
     *
     * @param guild the guild that was joined.
     * @return      a mocked {@link GuildJoinEvent}.
     */
    public static GuildJoinEvent getGuildJoinEvent(Guild guild) {
        GuildJoinEvent event = mock(GuildJoinEvent.class);
        JDA jda = getJDA();

        when(event.getGuild()).thenReturn(guild);
        when(event.getJDA()).thenReturn(jda);

        return event;
    }

    private static void mockOptions(CommandInteractionPayload event, Map<String, Object> options) {
        if (options == null) {
            options = new HashMap<>();
        }

        List<OptionMapping> optionMappings = new ArrayList<>();
        for (Map.Entry<String, Object> entry : options.entrySet()) {
            OptionMapping mapping = mock(OptionMapping.class);

            when(mapping.getName()).thenReturn(entry.getKey());
            // why
            when(mapping.getAsAttachment()).thenAnswer(inv -> entry.getValue());
            when(mapping.getAsString()).thenAnswer(inv -> entry.getValue());
            when(mapping.getAsBoolean()).thenAnswer(inv -> entry.getValue());
            when(mapping.getAsLong()).thenAnswer(inv -> entry.getValue());
            when(mapping.getAsInt()).thenAnswer(inv -> entry.getValue());
            when(mapping.getAsDouble()).thenAnswer(inv -> entry.getValue());
            when(mapping.getAsMentionable()).thenAnswer(inv -> entry.getValue());
            when(mapping.getAsMember()).thenAnswer(inv -> entry.getValue());
            when(mapping.getAsUser()).thenAnswer(inv -> entry.getValue());
            when(mapping.getAsRole()).thenAnswer(inv -> entry.getValue());
            when(mapping.getAsChannel()).thenAnswer(inv -> entry.getValue());

            optionMappings.add(mapping);
        }

        when(event.getOptions()).thenReturn(optionMappings);

        when(event.getOption(anyString())).thenAnswer(invocation -> {
            Optional<OptionMapping> mapping = optionMappings.stream()
                    .filter(optionMapping -> optionMapping.getName().equals(invocation.getArgument(0)))
                    .findFirst();
            if (mapping.isPresent()) {
                return mapping.get();
            }
            throw new IllegalArgumentException("Unknown option: " + invocation.getArgument(0));
        });

        when(event.getOptionsByName(anyString())).thenAnswer(invocation -> optionMappings.stream()
                .filter(optionMapping -> optionMapping.getName().equals(invocation.getArgument(0))).toList());
    }

    private static void mockCallbacks(IReplyCallback event, MessageChannel channel,
                                      Callback<Message> messageCallback, Callback<Boolean> deferReply) {
        when(event.reply(anyString())).thenAnswer(invocation ->
                getReplyCallbackAction(getMessage(invocation.getArgument(0), channel), messageCallback));
        when(event.reply(any(MessageCreateData.class))).thenAnswer(invocation ->
                getReplyCallbackAction(getMessage(invocation.getArgument(0, MessageCreateData.class).getContent(),
                        channel), messageCallback));
        when(event.replyEmbeds(anyList())).thenAnswer(invocation ->
                getReplyCallbackAction(getMessage(null, invocation.getArgument(0), channel),
                        messageCallback));
        when(event.replyEmbeds(any(MessageEmbed.class), any(MessageEmbed[].class))).thenAnswer(invocation -> {
            List<MessageEmbed> embeds = invocation.getArguments().length == 1 ? new ArrayList<>() :
                    Arrays.asList(invocation.getArgument(1));
            embeds.add(invocation.getArgument(0));
            return getReplyCallbackAction(getMessage(null, embeds, channel), messageCallback);
        });

        when(event.deferReply()).thenAnswer(invocation -> {
            deferReply.callback(false);
            return mock(ReplyCallbackAction.class);
        });

        when(event.deferReply(any(Boolean.class))).thenAnswer(invocation -> {
            deferReply.callback(invocation.getArgument(0));
            return mock(ReplyCallbackAction.class);
        });
    }

    /**
     * Get a mocked instance of JDA.
     *
     * @return a mocked instance of {@link JDA}.
     */
    @NotNull
    public static JDA getJDA() {
        return getJDA("Test", "0000");
    }


    /**
     * Get a mocked instance of JDA with the specified name and discriminator.
     *
     * @param name              the name of the self user.
     * @param discriminator     the discriminator of the self user.
     * @return                  a mocked instance of {@link JDA}.
     * @throws RuntimeException if interrupted while awaiting ready status.
     */
    @NotNull
    public static JDA getJDA(String name, String discriminator) {
        try {
            JDA jda = mock(JDAImpl.class);
            when(jda.getStatus()).thenAnswer(invocation -> JDA.Status.CONNECTED);
            when(jda.unloadUser(anyLong())).thenAnswer(invocation -> true);
            when(jda.awaitReady()).thenAnswer(invocation -> jda);
            when(jda.awaitStatus(any(JDA.Status.class))).thenAnswer(invocation -> jda);
            when(jda.awaitStatus(any(JDA.Status.class), any(JDA.Status[].class))).thenAnswer(invocation -> jda);
            when(jda.getSelfUser()).thenAnswer(invocation -> getSelfUser(name, discriminator));
            when(jda.getInviteUrl(any(Permission[].class))).thenAnswer(invocation -> "http://fake.url");
            when(jda.getInviteUrl(anyCollection())).thenAnswer(invocation -> "http://fake.url");
            return jda;
        } catch (InterruptedException e) {
            throw new RuntimeException("This should not be reachable", e);
        }
    }

    /**
     * Get a mocked {@link MessageChannel}.
     *
     * @param name              the name of the channel.
     * @param id                the id of the channel.
     * @param messageCallback   the callback used for returning the {@link Message} when for example
     *                          {@link MessageChannel#sendMessage(CharSequence)} or other methods are called.
     * @return                  a mocked {@link MessageChannel}.
     */
    public static MessageChannel getMessageChannel(String name, long id, Callback<Message> messageCallback) {
        MessageChannel channel = mock(MessageChannel.class, withSettings()
                .extraInterfaces(TextChannel.class, NewsChannel.class, MessageChannelUnion.class));
        when(channel.getName()).thenAnswer(invocation -> name);
        when(channel.getIdLong()).thenAnswer(invocation -> id);
        when(channel.getId()).thenAnswer(invocation -> String.valueOf(id));

        when(channel.sendMessage(any(CharSequence.class)))
                .thenAnswer(invocation -> getMessageCreateAction(messageCallback,
                        getMessage(invocation.getArgument(0), channel)));

        when(channel.sendMessage(any(MessageCreateData.class)))
                .thenAnswer(invocation -> getMessageCreateAction(messageCallback,
                        invocation.getArgument(0)));

        when(channel.sendMessageEmbeds(any(MessageEmbed.class), any(MessageEmbed[].class)))
                .thenAnswer(invocation -> {
                    List<MessageEmbed> embeds = invocation.getArguments().length == 1 ? new ArrayList<>() :
                            Arrays.asList(invocation.getArgument(1));
                    embeds.add(invocation.getArgument(0));
                    return getMessageCreateAction(messageCallback, getMessage(null, embeds, channel));
                });

        when(channel.sendMessageEmbeds(anyList()))
                .thenAnswer(invocation -> getMessageCreateAction(messageCallback,
                        getMessage(null, invocation.getArgument(0), channel)));

        return channel;
    }

    /**
     * Get a mocked {@link MessageCreateAction}.
     *
     * @param messageCallback   the message callback that well return the {@link Message} when
     *                          {@link MessageCreateAction#queue()} is executed.
     * @param message           the message that will be used by the {@link Callback}.
     * @return                  a mocked {@link MessageCreateAction}.
     */
    public static MessageCreateAction getMessageCreateAction(Callback<Message> messageCallback, Message message) {
        MessageCreateAction messageAction = mock(MessageCreateAction.class);
        Mockito.doAnswer(invocation -> {
            messageCallback.callback(message);
            return null;
        }).when(messageAction).queue();
        return messageAction;
    }

    /**
     * Get a mocked {@link Message}.
     *
     * @param content   the content of the message. This is the raw, displayed and stripped content.
     * @param channel   the {@link MessageChannel} the message would be sent in.
     * @return          a mocked {@link Message}.
     */
    public static Message getMessage(String content, MessageChannel channel) {
        return getMessage(content, new ArrayList<>(), channel);
    }

    /**
     * Get a mocked {@link Message}.
     *
     * @param content   the content of the message. This is the raw, displayed and stripped content.
     * @param embeds    a list of {@link MessageEmbed}s that this message contains.
     * @param channel   the {@link MessageChannel} the message would be sent in.
     * @return          a mocked {@link Message}.
     */
    public static Message getMessage(String content, List<MessageEmbed> embeds, MessageChannel channel) {
        Message message = mock(Message.class);
        when(message.getContentRaw()).thenAnswer(invocation -> content);
        when(message.getContentDisplay()).thenAnswer(invocation -> content);
        when(message.getContentStripped()).thenAnswer(invocation -> content);
        when(message.getChannel()).thenAnswer(invocation -> channel);
        when(message.getEmbeds()).thenAnswer(invocation -> embeds);
        return message;
    }

    /**
     * Get a mocked {@link Member}. To get a mocked {@link User} use {@link Member#getUser()}.
     *
     * @param name          the name of the user.
     * @param discriminator the discriminator of the user.
     * @return              a mocked {@link Member}.
     */
    public static Member getMember(String name, String discriminator) {
        Member member = mock(Member.class);
        when(member.getEffectiveName()).thenAnswer(invocation -> name);
        when(member.getNickname()).thenAnswer(invocation -> name);

        User user = mock(User.class);
        when(user.getName()).thenAnswer(invocation -> name);
        when(user.getDiscriminator()).thenAnswer(invocation -> discriminator);
        when(user.getAsTag()).thenAnswer(invocation -> name + "#" + discriminator);

        when(member.getUser()).thenAnswer(invocation -> user);

        return member;
    }

    /**
     * Get a mocked {@link GuildChannelUnion}.
     *
     * @param name  the name of the channel
     * @param id    the id of the channel
     * @return      the mocked {@link GuildChannelUnion}
     */
    public static GuildChannelUnion getGuildChannelUnion(String name, long id, Callback<Message> callback) {
        GuildChannelUnion channelUnion = mock(GuildChannelUnion.class);

        when(channelUnion.getName()).thenAnswer(invocation -> name);
        when(channelUnion.getId()).thenAnswer(invocation -> id + "");
        when(channelUnion.getIdLong()).thenAnswer(invocation -> id);

        TextChannel channel = (TextChannel) getMessageChannel(name, id, callback);

        when(channelUnion.asTextChannel()).thenAnswer(invocation -> channel);
        when(channelUnion.asGuildMessageChannel()).thenAnswer(invocation -> channel);
        when(channelUnion.asNewsChannel()).thenAnswer(invocation -> channel);

        return channelUnion;
    }

    /**
     * Get a mocked {@link SelfUser}.
     *
     * @param name          the name of the user.
     * @param discriminator the discriminator of the user.
     * @return              a mocked {@link SelfUser}.
     */
    @NotNull
    public static SelfUser getSelfUser(String name, String discriminator) {
        SelfUser selfUser = mock(SelfUser.class);

        when(selfUser.getName()).thenAnswer(invocation -> name);
        when(selfUser.getDiscriminator()).thenAnswer(invocation -> discriminator);
        when(selfUser.getAsTag()).thenAnswer(invocation -> name + "#" + discriminator);
        when(selfUser.getId()).thenAnswer(invocation -> "0");
        when(selfUser.getIdLong()).thenAnswer(invocation -> 0L);

        return selfUser;
    }

    /**
     * Get a mocked {@link Guild}.
     *
     * @param name  the name of the guild.
     * @return      a mocked {@link Guild}
     */
    public static Guild getGuild(String name) {
        Guild guild = mock(Guild.class);

        when(guild.getName()).thenReturn(name);
        when(guild.getId()).thenReturn("0");
        when(guild.getIdLong()).thenReturn(0L);

        return guild;
    }

    protected static <T extends CommandInteraction> T
        getCommandInteraction(MessageChannel channel, Member member, String name, String subcommand, String subgroup,
                              Map<String, Object> options, Class<T> clazz) {
        T interaction = mock(clazz);

        when(interaction.getName()).thenReturn(name);
        when(interaction.getSubcommandName()).thenReturn(subcommand);
        when(interaction.getSubcommandGroup()).thenReturn(subgroup);
        when(interaction.getMessageChannel()).thenReturn(channel);
        when(interaction.getChannel()).thenAnswer(invocation -> channel);
        when(interaction.getChannelId()).thenAnswer(invocation -> channel.getId());
        when(interaction.getChannelIdLong()).thenAnswer(invocation -> channel.getIdLong());
        when(interaction.getChannelType()).thenAnswer(invocation -> channel.getType());
        when(interaction.getMember()).thenReturn(member);

        mockOptions(interaction, options);

        return interaction;
    }

    /**
     * Get a mocked {@link MessageContextInteraction}.
     *
     * @param channel       the channel the interaction was triggered.
     * @param member        the member the interaction was triggered by.
     * @param name          the name of the application command the interaction triggered.
     * @param options       options for application command.
     * @return              a mocked {@link MessageContextInteraction}.
     */
    public static MessageContextInteraction getMessageContextInteraction(MessageChannel channel, Member member,
                                                                         String name, Map<String, Object> options) {
        return getCommandInteraction(channel, member, name, null, null, options,
                MessageContextInteraction.class);
    }
}
