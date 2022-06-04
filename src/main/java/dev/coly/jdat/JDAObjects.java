package dev.coly.jdat;

import dev.coly.util.Callback;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import net.dv8tion.jda.internal.JDAImpl;
import org.jetbrains.annotations.NotNull;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * A class for provided mocked JDA objects.
 * <br>
 * <strong>Note: not all methods are currently mocked. Calling some methods will result in undefined behavior.</strong>
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
     *                          {@link MessageChannel#sendMessage(Message)} or
     *                          {@link MessageChannel#sendMessageEmbeds(Collection)} for example.
     * @return                  a mocked {@link SlashCommandInteractionEvent}.
     */
    public static SlashCommandInteractionEvent getSlashCommandInteractionEvent(MessageChannel channel, String name,
                                                                               String subcommandName,
                                                                               String subcommandGroup,
                                                                               Map<String, Object> options,
                                                                               Callback<Message> messageCallback) {
        return getSlashCommandInteractionEvent(channel, name, subcommandName, subcommandGroup, options, messageCallback,
                new Callback<>());
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
     *                          {@link MessageChannel#sendMessage(Message)} or
     *                          {@link MessageChannel#sendMessageEmbeds(Collection)} for example.
     * @return                  a mocked {@link SlashCommandInteractionEvent}.
     */
    public static SlashCommandInteractionEvent getSlashCommandInteractionEvent(MessageChannel channel, String name,
                                                                               String subcommandName,
                                                                               String subcommandGroup,
                                                                               Map<String, Object> options,
                                                                               Callback<Message> messageCallback,
                                                                               Callback<Boolean> deferReply) {
        SlashCommandInteractionEvent event = mock(SlashCommandInteractionEvent.class);
        when(event.getName()).thenAnswer(invocation -> name);
        when(event.getSubcommandName()).thenAnswer(invocation -> subcommandName);
        when(event.getSubcommandGroup()).thenAnswer(invocation -> subcommandGroup);
        when(event.getChannel()).thenAnswer(invocation -> channel);

        when(event.getOption(anyString())).thenAnswer(invocation -> {
            OptionMapping mapping = mock(OptionMapping.class);
            // why
            when(mapping.getAsAttachment()).thenAnswer(inv -> options.get((String) invocation.getArgument(0)));
            when(mapping.getAsString()).thenAnswer(inv -> options.get((String) invocation.getArgument(0)));
            when(mapping.getAsBoolean()).thenAnswer(inv -> options.get((String) invocation.getArgument(0)));
            when(mapping.getAsLong()).thenAnswer(inv -> options.get((String) invocation.getArgument(0)));
            when(mapping.getAsInt()).thenAnswer(inv -> options.get((String) invocation.getArgument(0)));
            when(mapping.getAsDouble()).thenAnswer(inv -> options.get((String) invocation.getArgument(0)));
            when(mapping.getAsMentionable()).thenAnswer(inv -> options.get((String) invocation.getArgument(0)));
            when(mapping.getAsMember()).thenAnswer(inv -> options.get((String) invocation.getArgument(0)));
            when(mapping.getAsUser()).thenAnswer(inv -> options.get((String) invocation.getArgument(0)));
            when(mapping.getAsRole()).thenAnswer(inv -> options.get((String) invocation.getArgument(0)));
            when(mapping.getAsGuildChannel()).thenAnswer(inv -> options.get((String) invocation.getArgument(0)));
            when(mapping.getAsMessageChannel()).thenAnswer(inv -> options.get((String) invocation.getArgument(0)));
            when(mapping.getAsTextChannel()).thenAnswer(inv -> options.get((String) invocation.getArgument(0)));
            when(mapping.getAsNewsChannel()).thenAnswer(inv -> options.get((String) invocation.getArgument(0)));
            when(mapping.getAsThreadChannel()).thenAnswer(inv -> options.get((String) invocation.getArgument(0)));
            when(mapping.getAsAudioChannel()).thenAnswer(inv -> options.get((String) invocation.getArgument(0)));
            when(mapping.getAsVoiceChannel()).thenAnswer(inv -> options.get((String) invocation.getArgument(0)));
            when(mapping.getAsStageChannel()).thenAnswer(inv -> options.get((String) invocation.getArgument(0)));

            return mapping;
        });

        when(event.reply(anyString())).thenAnswer(invocation ->
                getReplyCallbackAction(getMessage(invocation.getArgument(0), channel), messageCallback));
        when(event.reply(any(Message.class))).thenAnswer(invocation ->
                getReplyCallbackAction(invocation.getArgument(0), messageCallback));
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
     * Get a mocked {@link JDA}.
     *
     * @return  a mocked {@link JDA}.
     */
    @NotNull
    public static JDA getJDA() {
        try {
            JDA jda = mock(JDAImpl.class);
            when(jda.getStatus()).thenAnswer(invocation -> JDA.Status.CONNECTED);
            when(jda.unloadUser(anyLong())).thenAnswer(invocation -> true);
            when(jda.awaitReady()).thenAnswer(invocation -> jda);
            when(jda.awaitStatus(any(JDA.Status.class))).thenAnswer(invocation -> jda);
            when(jda.awaitStatus(any(JDA.Status.class), any(JDA.Status[].class))).thenAnswer(invocation -> jda);
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
     *                          {@link MessageChannel#sendMessage(Message)} or other methods are called.
     * @return                  a mocked {@link MessageChannel}.
     */
    public static MessageChannel getMessageChannel(String name, long id, Callback<Message> messageCallback) {
        MessageChannel channel = mock(MessageChannel.class);
        when(channel.getName()).thenAnswer(invocation -> name);
        when(channel.getIdLong()).thenAnswer(invocation -> id);
        when(channel.getId()).thenAnswer(invocation -> String.valueOf(id));

        when(channel.sendMessage(any(CharSequence.class)))
                .thenAnswer(invocation -> getMessageAction(messageCallback,
                        getMessage(invocation.getArgument(0), channel)));

        when(channel.sendMessage(any(Message.class)))
                .thenAnswer(invocation -> getMessageAction(messageCallback,
                        invocation.getArgument(0)));

        when(channel.sendMessageEmbeds(any(MessageEmbed.class), any(MessageEmbed[].class)))
                .thenAnswer(invocation -> {
                    List<MessageEmbed> embeds = invocation.getArguments().length == 1 ? new ArrayList<>() :
                            Arrays.asList(invocation.getArgument(1));
                    embeds.add(invocation.getArgument(0));
                    return getMessageAction(messageCallback, getMessage(null, embeds, channel));
                });

        when(channel.sendMessageEmbeds(anyList()))
                .thenAnswer(invocation -> getMessageAction(messageCallback,
                        getMessage(null, invocation.getArgument(0), channel)));

        return channel;
    }

    /**
     * Get a mocked {@link MessageAction}.
     *
     * @param messageCallback   the message callback that well return the {@link Message} when
     *                          {@link MessageAction#queue()} is executed.
     * @param message           the message that will be used by the {@link Callback}.
     * @return                  a mocked {@link MessageAction}.
     */
    public static MessageAction getMessageAction(Callback<Message> messageCallback, Message message) {
        MessageAction messageAction = mock(MessageAction.class);
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

}
