package dev.coly.jdat;

import dev.coly.util.Callback;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.NewsChannel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.channel.unions.GuildChannelUnion;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.entities.messages.MessagePoll;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.GenericComponentInteractionCreateEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.Interaction;
import net.dv8tion.jda.api.interactions.callbacks.IMessageEditCallback;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import net.dv8tion.jda.api.interactions.commands.CommandInteraction;
import net.dv8tion.jda.api.interactions.commands.CommandInteractionPayload;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.context.MessageContextInteraction;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Component;
import net.dv8tion.jda.api.interactions.components.ComponentInteraction;
import net.dv8tion.jda.api.interactions.components.LayoutComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonInteraction;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectInteraction;
import net.dv8tion.jda.api.interactions.modals.Modal;
import net.dv8tion.jda.api.interactions.modals.ModalInteraction;
import net.dv8tion.jda.api.interactions.modals.ModalMapping;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import net.dv8tion.jda.api.utils.messages.MessagePollData;
import net.dv8tion.jda.internal.JDAImpl;
import net.dv8tion.jda.internal.entities.EntityBuilder;
import org.jetbrains.annotations.NotNull;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
     * @param channel         the channel this event would be executed.
     * @param name            the name of the slash command.
     * @param subcommandName  the name of the subcommand of the slash command.
     * @param subcommandGroup the subcommand group of the slash command.
     * @param options         a map with all options for the slash command a user would have inputted.
     * @param messageCallback a callback to receive messages that would be sent back to the channel with.
     *                        {@link MessageChannel#sendMessage(CharSequence)} or
     *                        {@link MessageChannel#sendMessageEmbeds(Collection)} for example.
     * @return a mocked {@link SlashCommandInteractionEvent}.
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
     * @param channel         the channel this event would be executed.
     * @param name            the name of the slash command.
     * @param subcommandName  the name of the subcommand of the slash command.
     * @param subcommandGroup the subcommand group of the slash command.
     * @param options         a map with all options for the slash command a user would have inputted.
     * @param messageCallback a callback to receive messages that would be sent back to the channel with.
     * @param deferReply      a callback that is called when a deferred reply is called. The boolean is true when
     *                        the message is ephemeral and false if not.
     *                        {@link MessageChannel#sendMessage(CharSequence)} or
     *                        {@link MessageChannel#sendMessageEmbeds(Collection)} for example.
     * @return a mocked {@link SlashCommandInteractionEvent}.
     */
    public static SlashCommandInteractionEvent getSlashCommandInteractionEvent(MessageChannel channel, String name,
                                                                               String subcommandName,
                                                                               String subcommandGroup,
                                                                               Map<String, Object> options,
                                                                               Callback<Message> messageCallback,
                                                                               Callback<Boolean> deferReply) {
        Member member = getMember("User", "0000");
        CommandInteraction interaction = getCommandInteraction(channel, member, name, subcommandName, subcommandGroup,
                options, messageCallback, deferReply, CommandInteraction.class);
        SlashCommandInteractionEvent event = getCommandInteractionEvent(interaction, SlashCommandInteractionEvent.class);

        mockReplyWithInteraction(event, interaction);

        return event;
    }

    /**
     * Get a mocked {@link ReplyCallbackAction}.
     *
     * @param message         the message that this reply should produce.
     * @param messageCallback the callback for receiving the message.
     * @return a mocked {@link ReplyCallbackAction}.
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
     * @param channel the channel the message would be sent in.
     * @param message the message that would be sent.
     * @param member  the member that would send this message.
     * @return a mocked {@link MessageReceivedEvent}.
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
     * @param interaction       the interaction triggered this event.
     * @return a mocked {@link MessageContextInteractionEvent}.
     */
    public static MessageContextInteractionEvent getMessageContextInteractionEvent(
            MessageContextInteraction interaction) {
        return getCommandInteractionEvent(interaction, MessageContextInteractionEvent.class);
    }

    /**
     * Get a mocked {@link ButtonInteractionEvent}.
     *
     * @param interaction the interaction that triggered this event.
     * @return a mocked {@link ButtonInteractionEvent}.
     */
    public static ButtonInteractionEvent getButtonInteractionEvent(
            ButtonInteraction interaction, Callback<Void> deferEditCallback
    ) {
        return getGenericComponentInteractionCreateEvent(interaction, deferEditCallback, ButtonInteractionEvent.class);
    }

    /**
     * Get a mocked {@link ModalInteractionEvent}.
     *
     * @param interaction the interaction that triggered this event
     * @return a mocked {@link ModalInteractionEvent}.
     */
    public static ModalInteractionEvent getModalInteractionEvent(ModalInteraction interaction) {
        return getGenericInteractionCreateEvent(interaction, ModalInteractionEvent.class);
    }

    protected static <T extends GenericCommandInteractionEvent> T getCommandInteractionEvent(
            CommandInteraction interaction, Class<T> clazz) {
        T event = getGenericInteractionCreateEvent(interaction, clazz);

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

        mockReplyWithInteraction(event, interaction);

        return event;
    }

    protected static <T extends GenericComponentInteractionCreateEvent> T getGenericComponentInteractionCreateEvent(
            ComponentInteraction interaction, Callback<Void> deferEditCallback, Class<T> clazz
    ) {
        T event = getGenericInteractionCreateEvent(interaction, clazz);

        when(event.getComponentId()).thenAnswer(invocation -> interaction.getComponentId());
        when(event.getComponent()).thenAnswer(invocation -> interaction.getComponent());
        when(event.getMessageId()).thenAnswer(invocation -> interaction.getMessageId());
        when(event.getMessageIdLong()).thenAnswer(invocation -> interaction.getMessageIdLong());
        when(event.getComponentType()).thenAnswer(invocation -> interaction.getComponentType());

        when(event.deferEdit()).thenAnswer(invocation -> interaction.deferReply());
        when(event.replyModal(any(Modal.class))).thenAnswer(invocation ->
                interaction.replyModal(invocation.getArgument(0)));
        when(event.replyWithPremiumRequired()).thenAnswer(invocation ->
                interaction.replyWithPremiumRequired());

        mockReplyWithEditInteraction((IReplyMessageEditCallback) event, (IReplyMessageEditCallback) interaction);

        when(event.deferEdit()).thenAnswer(invocation -> {
            deferEditCallback.callback(Void.TYPE.cast(null));
            return mock(ReplyCallbackAction.class);
        });

        return event;
    }

    protected static <T extends GenericInteractionCreateEvent> T getGenericInteractionCreateEvent(
            Interaction interaction, Class<T> clazz
    ) {
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
        when(event.getType()).thenAnswer(invocation -> interaction.getType());

        return event;
    }

    /**
     * Get a mocked {@link GuildJoinEvent}.
     *
     * @param guild the guild that was joined.
     * @return a mocked {@link GuildJoinEvent}.
     */
    public static GuildJoinEvent getGuildJoinEvent(Guild guild) {
        GuildJoinEvent event = mock(GuildJoinEvent.class);
        JDA jda = getJDA();

        when(event.getGuild()).thenReturn(guild);
        when(event.getJDA()).thenReturn(jda);

        return event;
    }

    protected static void mockOptions(CommandInteractionPayload event, Map<String, Object> options) {
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
     * @param name          the name of the self user.
     * @param discriminator the discriminator of the self user.
     * @return a mocked instance of {@link JDA}.
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
     * @param name            the name of the channel.
     * @param id              the id of the channel.
     * @param messageCallback the callback used for returning the {@link Message} when for example
     *                        {@link MessageChannel#sendMessage(CharSequence)} or other methods are called.
     * @return a mocked {@link MessageChannel}.
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
     * @param messageCallback the message callback that well return the {@link Message} when
     *                        {@link MessageCreateAction#queue()} is executed.
     * @param message         the message that will be used by the {@link Callback}.
     * @return a mocked {@link MessageCreateAction}.
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
     * @param content the content of the message. This is the raw, displayed and stripped content.
     * @param channel the {@link MessageChannel} the message would be sent in.
     * @return a mocked {@link Message}.
     */
    public static Message getMessage(String content, MessageChannel channel) {
        return getMessage(content, new ArrayList<>(), channel);
    }

    /**
     * Get a mocked {@link Message}.
     *
     * @param content the content of the message. This is the raw, displayed and stripped content.
     * @param embeds  a list of {@link MessageEmbed}s that this message contains.
     * @param channel the {@link MessageChannel} the message would be sent in.
     * @return a mocked {@link Message}.
     */
    public static Message getMessage(String content, List<MessageEmbed> embeds, MessageChannel channel) {
        return getMessage(content, embeds, null, List.of(), null, List.of(), channel);
    }

    /**
     * Get a mocked {@link Message}.
     *
     * @param content       the content of the message. This is the raw, displayed, and stripped content.
     * @param embeds        a list of {@link MessageEmbed}s that this message contains.
     * @param poll          a poll attached to the message.
     * @param attachments   a list of attachments to this message. This will probably be files.
     * @param activity      an activity attached to the message.
     * @param actionRows    a list of actions rows with actions attached to the message.
     * @param channel       the {@link MessageChannel} the message would be sent in.
     * @return a mocked {@link Message}.
     */
    public static Message getMessage(String content, List<MessageEmbed> embeds, MessagePoll poll,
                                     List<Message.Attachment> attachments, Activity activity, List<ActionRow> actionRows,
                                     MessageChannel channel) {
        Message message = mock(Message.class);
        when(message.getContentRaw()).thenAnswer(invocation -> content);
        when(message.getContentDisplay()).thenAnswer(invocation -> content);
        when(message.getContentStripped()).thenAnswer(invocation -> content);

        when(message.getChannel()).thenAnswer(invocation -> channel);
        when(message.getChannelId()).thenAnswer(invocation -> channel.getId());
        when(message.getChannelIdLong()).thenAnswer(invocation -> channel.getIdLong());
        when(message.getChannelType()).thenAnswer(invocation -> channel.getType());

        when(message.getJDA()).thenAnswer(invocation -> getJDA());
        Member member = getMember("Author", "0000");
        when(message.getAuthor()).thenAnswer(invocation -> member.getUser());
        when(message.getMember()).thenAnswer(invocation -> member);

        when(message.getEmbeds()).thenAnswer(invocation -> embeds);
        when(message.getPoll()).thenAnswer(invocation -> poll);
        when(message.getAttachments()).thenAnswer(invocation -> attachments);
        when(message.getActivity()).thenAnswer(invocation -> activity);
        when(message.getActionRows()).thenAnswer(invocation -> actionRows);
        return message;
    }

    /**
     * Get a mocked {@link Member}. To get a mocked {@link User} use {@link Member#getUser()}.
     *
     * @param name          the name of the user.
     * @param discriminator the discriminator of the user.
     * @return a mocked {@link Member}.
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
     * @param name the name of the channel
     * @param id   the id of the channel
     * @return the mocked {@link GuildChannelUnion}
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
     * @return a mocked {@link SelfUser}.
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
     * @param name the name of the guild.
     * @return a mocked {@link Guild}
     */
    public static Guild getGuild(String name) {
        Guild guild = mock(Guild.class);

        when(guild.getName()).thenReturn(name);
        when(guild.getId()).thenReturn("0");
        when(guild.getIdLong()).thenReturn(0L);

        return guild;
    }

    /**
     * Get a real but not functional {@link MessagePoll} from data.
     *
     * @param data  the data for creating the poll.
     * @return      a real but not functional {@link MessagePoll}.
     */
    public static MessagePoll getMessagePoll(MessagePollData data) {
        return EntityBuilder.createMessagePoll( data.toData());
    }

    public static List<Message.Attachment> getAttachments(List<FileUpload> files) {
        List<Message.Attachment> attachments = new ArrayList<>();
        for (FileUpload file : files) {
            int size = -1;
            try {
                size = file.getData().readAllBytes().length;
            } catch (IOException ignored) {}
            attachments.add(new Message.Attachment(0, "https://cdn.fake/" + file.getName(),
                    "https://proxy.fake/" + file.getName(), file.getName(), "unknwon",
                    file.getDescription(), size, -1, -1, false, null, -1,
                    (JDAImpl) getJDA()));
        }
        return attachments;
    }

    /**
     * Get a mocked {@link MessageContextInteraction}.
     *
     * @param channel           the channel the interaction was triggered.
     * @param member            the member the interaction was triggered by.
     * @param name              the name of the application command the interaction triggered.
     * @param options           options for application command.
     * @param messageCallback   the callback used for message reply
     * @param deferReply        the callback used for defer reply
     * @return a mocked {@link MessageContextInteraction}.
     */
    public static MessageContextInteraction
    getMessageContextInteraction(MessageChannel channel, Member member, String name, Map<String, Object> options,
                                 Callback<Message> messageCallback, Callback<Boolean> deferReply) {
        return getCommandInteraction(channel, member, name, null, null, options,
                messageCallback, deferReply, MessageContextInteraction.class);
    }

    public static ButtonInteraction
    getButtonInteraction(MessageChannel channel, Button button, String componentId, Member member,
                         Callback<Message> messageCallback, Callback<Boolean> deferReply, Callback<Void> deferEditRepl) {
        ButtonInteraction interaction = getComponentInteraction(channel, member, button, componentId, messageCallback,
                deferReply, deferEditRepl, ButtonInteraction.class);

        when(interaction.getButton()).thenReturn(button);
        when(interaction.getComponent()).thenReturn(button);
        when(interaction.editButton(any(Button.class))).thenAnswer(invocation -> {
            Button newButton = invocation.getArgument(0);

            Message message = interaction.getMessage();
            List<ActionRow> components = new ArrayList<>(message.getActionRows());
            LayoutComponent.updateComponent(components, interaction.getComponentId(), newButton);

            if (interaction.isAcknowledged())
                return interaction.getHook().editMessageComponentsById(message.getId(), components)
                        .map(it -> null);
            else
                return interaction.editComponents(components).map(it -> null);
        });

        return interaction;
    }

    public static ModalInteraction getModalInteraction(MessageChannel channel, Message message, String modalId,
                                                       List<ModalMapping> values, Member member) {
        ModalInteraction interaction = getInteraction(channel, member, ModalInteraction.class);

        when(interaction.getModalId()).thenReturn(modalId);
        when(interaction.getMessage()).thenReturn(message);
        when(interaction.getChannel()).thenReturn(message.getChannel());
        when(interaction.getGuildChannel()).thenReturn(message.getGuildChannel());

        when(interaction.getValues()).thenReturn(values);
        when(interaction.getValue(anyString())).thenAnswer(invocation ->
                values.stream()
                        .filter(mapping -> mapping.getId().equals(invocation.getArgument(0)))
                        .findFirst()
                        .orElse(null)
        );
        return interaction;
    }

    public static StringSelectInteraction
    getStringSelectInteraction(MessageChannel channel, Button button, String componentId, Member member,
                               Callback<Message> messageCallback, Callback<Boolean> deferReply, Callback<Void> deferEditRepl) {
        return getComponentInteraction(channel, member, button, componentId, messageCallback, deferReply, deferEditRepl,
                StringSelectInteraction.class);
    }

    protected static <T extends CommandInteraction> T
    getCommandInteraction(MessageChannel channel, Member member, String name, String subcommand, String subgroup,
                          Map<String, Object> options, Callback<Message> messageCallback,
                          Callback<Boolean> deferReply, Class<T> clazz) {
        T interaction = getInteraction(channel, member, clazz);

        when(interaction.getName()).thenReturn(name);
        when(interaction.getSubcommandName()).thenReturn(subcommand);
        when(interaction.getSubcommandGroup()).thenReturn(subgroup);

        mockOptions(interaction, options);
        mockReplyCallbacks(interaction, channel, messageCallback, deferReply);

        return interaction;
    }

    protected static <T extends ComponentInteraction> T
    getComponentInteraction(MessageChannel channel, Member member, Component component, String componentId,
                            Callback<Message> messageCallback, Callback<Boolean> deferReply,
                            Callback<Void> deferEditReply, Class<T> clazz) {
        T interaction = getInteraction(channel, member, clazz);

        mockReplyCallbacks((IReplyMessageEditCallback) interaction, channel, messageCallback, deferReply,
                deferEditReply);

        when(interaction.getComponent()).thenAnswer(invocation -> component);
        when(interaction.getComponentId()).thenAnswer(invocation -> componentId);
        when(interaction.getComponentType()).thenAnswer(invocation -> component.getType());

        when(interaction.deferEdit()).thenAnswer(invocation -> interaction.deferReply());
        when(interaction.replyModal(any(Modal.class))).thenAnswer(invocation ->
                interaction.replyModal(invocation.getArgument(0)));
        when(interaction.replyWithPremiumRequired()).thenAnswer(invocation ->
                interaction.replyWithPremiumRequired());

        return interaction;
    }

    protected static <T extends Interaction> T getInteraction(MessageChannel channel, Member member, Class<T> clazz) {
        T interaction = mock(clazz);

        when(interaction.getMessageChannel()).thenReturn(channel);
        when(interaction.getChannel()).thenAnswer(invocation -> channel);
        when(interaction.getChannelId()).thenAnswer(invocation -> channel.getId());
        when(interaction.getChannelIdLong()).thenAnswer(invocation -> channel.getIdLong());
        when(interaction.getChannelType()).thenAnswer(invocation -> channel.getType());
        when(interaction.getMember()).thenReturn(member);
        when(interaction.getJDA()).thenAnswer(invocation -> channel.getJDA());

        return interaction;
    }

    protected interface IReplyMessageEditCallback extends IReplyCallback, IMessageEditCallback {}

    protected static void mockReplyCallbacks(IReplyCallback callback, MessageChannel channel,
                                           Callback<Message> messageCallback, Callback<Boolean> deferReply) {
        when(callback.reply(anyString())).thenAnswer(invocation ->
                getReplyCallbackAction(getMessage(invocation.getArgument(0), channel), messageCallback));
        when(callback.reply(any(MessageCreateData.class))).thenAnswer(invocation ->
                getReplyCallbackAction(getMessage(invocation.getArgument(0, MessageCreateData.class).getContent(),
                        channel), messageCallback));

        when(callback.replyEmbeds(anyList())).thenAnswer(invocation ->
                getReplyCallbackAction(getMessage(null, invocation.getArgument(0), channel),
                        messageCallback));
        when(callback.replyEmbeds(any(MessageEmbed.class), any(MessageEmbed[].class))).thenAnswer(invocation -> {
            List<MessageEmbed> embeds = invocation.getArguments().length == 1 ? new ArrayList<>() :
                    Arrays.asList(invocation.getArgument(1));
            embeds.add(invocation.getArgument(0));
            return getReplyCallbackAction(getMessage(null, embeds, channel), messageCallback);
        });

        when(callback.replyComponents(anyList())).thenAnswer(invocation -> {
            List<ActionRow> actionRows = new ArrayList<>(invocation.getArgument(0));
            return getReplyCallbackAction(getMessage(null, null, null, List.of(), null,
                    actionRows, channel), messageCallback);
        });
        when(callback.replyComponents(any(LayoutComponent.class), any(LayoutComponent[].class))).thenAnswer(invocation -> {
            List<ActionRow> actionRows = invocation.getArguments().length == 1 ? new ArrayList<>() :
                    Arrays.asList(invocation.getArgument(1));
            actionRows.add(invocation.getArgument(0));
            return getReplyCallbackAction(getMessage(null, null, null, List.of(), null,
                    actionRows, channel), messageCallback);
        });

        when(callback.replyPoll(any(MessagePollData.class))).thenAnswer(invocation -> {
            MessagePoll poll = getMessagePoll(invocation.getArgument(0));
            return getReplyCallbackAction(getMessage(null, null, poll, List.of(), null,
                    List.of(), channel), messageCallback);
        });

        when(callback.replyFiles(anyList())).thenAnswer(invocation -> {
            List<FileUpload> files = invocation.getArgument(0);
            return getReplyCallbackAction(getMessage(null, null, null, getAttachments(files),
                   null, List.of(), channel), messageCallback);
        });
        when(callback.replyFiles(any(FileUpload[].class))).thenAnswer(invocation -> {
            List<FileUpload> files = invocation.getArguments().length == 1 ? new ArrayList<>() :
                    Arrays.asList(invocation.getArgument(1));
            files.add(invocation.getArgument(0));
            return getReplyCallbackAction(getMessage(null, null, null, getAttachments(files),
                    null, List.of(), channel), messageCallback);
        });

        when(callback.deferReply()).thenAnswer(invocation -> {
            deferReply.callback(false);
            return mock(ReplyCallbackAction.class);
        });
        when(callback.deferReply(any(Boolean.class))).thenAnswer(invocation -> {
            deferReply.callback(invocation.getArgument(0));
            return mock(ReplyCallbackAction.class);
        });
    }

    protected static void mockReplyCallbacks(IReplyMessageEditCallback callback, MessageChannel channel,
                                             Callback<Message> messageCallback, Callback<Boolean> deferReply,
                                             Callback<Void> deferEditReply) {
        mockReplyCallbacks(callback, channel, messageCallback, deferReply);

        when(callback.deferEdit()).thenAnswer(invocation -> {
            deferEditReply.callback(Void.TYPE.cast(null));
            return mock(ReplyCallbackAction.class);
        });
    }

    protected static void mockReplyWithInteraction(IReplyCallback callback, IReplyCallback interaction) {
        when(callback.reply(anyString())).thenAnswer(invocation ->
                interaction.reply((String) invocation.getArgument(0)));
        when(callback.reply(any(MessageCreateData.class))).thenAnswer(invocation ->
                interaction.reply((MessageCreateData) invocation.getArgument(0)));

        when(callback.replyEmbeds(anyList())).thenAnswer(invocation ->
                interaction.replyEmbeds(invocation.getArgument(0)));
        when(callback.replyEmbeds(any(MessageEmbed.class), any(MessageEmbed[].class))).thenAnswer(invocation -> {
            if (invocation.getArguments().length == 1) {
                return interaction.replyEmbeds(invocation.getArgument(0), new MessageEmbed[0]);
            } else {
                return interaction.replyEmbeds(invocation.getArgument(0), invocation.getArgument(1));
            }
        });

        when(callback.replyComponents(anyList())).thenAnswer(invocation ->
                interaction.replyComponents(invocation.getArgument(0)));
        when(callback.replyComponents(any(LayoutComponent.class), any(LayoutComponent[].class)))
                .thenAnswer(invocation ->

                        interaction.replyComponents(invocation.getArgument(0), invocation.getArgument(1)));
        when(callback.replyPoll(any(MessagePollData.class))).thenAnswer(invocation ->
                interaction.replyPoll(invocation.getArgument(0)));

        when(callback.replyFiles(anyList())).thenAnswer(invocation ->
                interaction.replyFiles((Collection<? extends FileUpload>) invocation.getArgument(0)));
        when(callback.replyFiles(any(FileUpload[].class))).thenAnswer(invocation ->
                interaction.replyFiles((Collection<? extends FileUpload>) invocation.getArgument(0)));

        when(callback.deferReply()).thenAnswer(invocation ->
                interaction.deferReply());
        when(callback.deferReply(any(Boolean.class))).thenAnswer(invocation ->
                interaction.deferReply(invocation.getArgument(0)));
    }

    protected static void mockReplyWithEditInteraction(IReplyMessageEditCallback callback,
                                                   IReplyMessageEditCallback interaction) {
        mockReplyWithInteraction(callback, interaction);

        when(callback.deferEdit()).thenAnswer(invocation -> interaction.deferEdit());
    }
}
