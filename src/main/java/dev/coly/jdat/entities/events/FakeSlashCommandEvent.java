package dev.coly.jdat.entities.events;

import dev.coly.jdat.JDAObjects;
import dev.coly.jdat.entities.actions.FakeReplyAction;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyAction;
import net.dv8tion.jda.internal.interactions.CommandInteractionImpl;
import net.dv8tion.jda.internal.interactions.InteractionHookImpl;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class FakeSlashCommandEvent extends SlashCommandEvent {

    private final CommandInteractionImpl interaction;
    private FakeReplyAction replyAction;

    public FakeSlashCommandEvent(@NotNull JDA api, CommandInteractionImpl interaction) {
        super(api, 0, interaction);
        this.interaction = interaction;
    }

    public FakeReplyAction awaitReturn() throws InterruptedException {
        this.replyAction.awaitReturn();
        return this.replyAction;
    }

    @NotNull
    @Override
    public ReplyAction reply(@NotNull Message message) {
        this.replyAction = new FakeReplyAction((InteractionHookImpl) this.interaction.getHook(), message);
        return this.replyAction;
    }

    @NotNull
    @Override
    public ReplyAction reply(@NotNull String content) {
        return reply(JDAObjects.getFakeMessage(content));
    }

    @NotNull
    @Override
    public ReplyAction replyEmbeds(@NotNull Collection<? extends MessageEmbed> embeds) {
        this.replyAction = new FakeReplyAction((InteractionHookImpl) this.interaction.getHook(), null);
        return this.replyAction.addEmbeds(embeds);
    }

    @NotNull
    @Override
    public ReplyAction replyEmbeds(@NotNull MessageEmbed embed, MessageEmbed @NotNull ... embeds) {
        List<MessageEmbed> list = new LinkedList<>(Arrays.asList(embeds));
        list.add(embed);
        return replyEmbeds(list);
    }

    @NotNull
    @Override
    public ReplyAction replyFormat(@NotNull String format, Object @NotNull ... args) {
        return reply(String.format(format, args));
    }

    @NotNull
    @Override
    public ReplyAction deferReply() {
        this.replyAction = new FakeReplyAction((InteractionHookImpl) this.interaction.getHook(), null);
        return replyAction.setDefer(true);
    }

    @NotNull
    @Override
    public User getUser() {
        return JDAObjects.getFakeUser();
    }

    @NotNull
    @Override
    public MessageChannel getChannel() {
        return JDAObjects.getFakeTextChannel();
    }

    @NotNull
    @Override
    public TextChannel getTextChannel() {
        return JDAObjects.getFakeTextChannel();
    }

    @NotNull
    @Override
    public ChannelType getChannelType() {
        return ChannelType.TEXT;
    }
}
