package dev.coly.jdat;

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
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JDAObjects {

    public static SlashCommandInteractionEvent getSlashCommandInteractionEvent(MessageChannel channel, String command,
                                                                               String subcommandName,
                                                                               String subcommandGroup,
                                                                               Map<String, Object> options,
                                                                               Consumer<Message> messageCallback) {
        SlashCommandInteractionEvent event = mock(SlashCommandInteractionEvent.class);
        when(event.getCommandString()).thenAnswer(invocation -> command);
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
            List<MessageEmbed> embeds = invocation.getArgument(1) == null ? new ArrayList<>() :
                    Arrays.asList(invocation.getArgument(0));
            embeds.add(invocation.getArgument(0));
            return getReplyCallbackAction(getMessage(null, embeds, channel), messageCallback);
        });

        return event;
    }

    private static ReplyCallbackAction getReplyCallbackAction(Message message, Consumer<Message> messageCallback) {
        ReplyCallbackAction action = mock(ReplyCallbackAction.class);

        Mockito.doAnswer(invocation -> {
            messageCallback.accept(message);
            return null;
        }).when(action).queue();
        return action;
    }

    public static MessageReceivedEvent getMessageReceivedEvent(MessageChannel channel, Message message, Member member) {
        MessageReceivedEvent event = mock(MessageReceivedEvent.class);
        when(event.getChannel()).thenAnswer(invocation -> channel);
        when(event.getMessage()).thenAnswer(invocation -> message);
        when(event.getMember()).thenAnswer(invocation -> member);
        when(event.getAuthor()).thenAnswer(invocation -> member.getUser());
        return event;
    }

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

    public static MessageChannel getMessageChannel(String name, long id, Consumer<Message> messageCallback) {
        MessageChannel channel = mock(MessageChannel.class);
        when(channel.getName()).thenAnswer(invocation -> name);
        when(channel.getIdLong()).thenAnswer(invocation -> id);
        when(channel.getId()).thenAnswer(invocation -> String.valueOf(id));

        when(channel.sendMessage(any(Message.class)))
                .thenAnswer(invocation -> getMessageAction(messageCallback,
                        invocation.getArgument(0)));

        when(channel.sendMessageEmbeds(any(MessageEmbed.class), any(MessageEmbed[].class)))
                .thenAnswer(invocation -> {
                    List<MessageEmbed> embeds = invocation.getArgument(1) == null ? new ArrayList<>() :
                            Arrays.asList(invocation.getArgument(0));
                    embeds.add(invocation.getArgument(0));
                    return getMessageAction(messageCallback, getMessage(null, embeds, channel));
                });

        when(channel.sendMessageEmbeds(anyList()))
                .thenAnswer(invocation -> getMessageAction(messageCallback,
                        getMessage(null, invocation.getArgument(0), channel)));

        return channel;
    }

    public static MessageAction getMessageAction(Consumer<Message> messageCallback, Message message) {
        MessageAction messageAction = mock(MessageAction.class);
        Mockito.doAnswer(invocation -> {
            messageCallback.accept(message);
            return null;
        }).when(messageAction).queue();
        return messageAction;
    }

    public static Message getMessage(String content, MessageChannel channel) {
        return getMessage(content, new ArrayList<>(), channel);
    }

    public static Message getMessage(String content, List<MessageEmbed> embeds, MessageChannel channel) {
        Message message = mock(Message.class);
        when(message.getContentRaw()).thenAnswer(invocation -> content);
        when(message.getContentDisplay()).thenAnswer(invocation -> content);
        when(message.getContentStripped()).thenAnswer(invocation -> content);
        when(message.getChannel()).thenAnswer(invocation -> channel);
        when(message.getEmbeds()).thenAnswer(invocation -> embeds);
        return message;
    }

    public static Member getMember(String name, String discriminator) {
        Member member = mock(Member.class);
        when(member.getEffectiveName()).thenAnswer(invocation -> name);
        when(member.getNickname()).thenAnswer(invocation -> name);

        User user = mock(User.class);
        when(user.getName()).thenAnswer(invocation -> name);
        when(user.getDiscriminator()).thenAnswer(invocation -> discriminator);

        when(member.getUser()).thenAnswer(invocation -> user);

        return member;
    }

}
