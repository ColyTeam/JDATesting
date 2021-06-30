package dev.coly.jdat.entities;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import net.dv8tion.jda.api.requests.restaction.pagination.ReactionPaginationAction;
import org.apache.commons.collections4.Bag;
import org.jetbrains.annotations.Nullable;

import java.time.OffsetDateTime;
import java.util.*;

public class FakeMessage implements Message {

    private final FakeTextChannel channel;
    private String contentRaw;
    private List<MessageEmbed> messageEmbeds;

    private final List<User> mentionedUsers = new ArrayList<>();
    private final List<Member> mentionedMembers = new ArrayList<>();
    private final List<Role> mentionedRoles = new ArrayList<>();
    private final List<TextChannel> mentionedChannels = new ArrayList<>();
    private User user;
    private Member member;

    public FakeMessage(FakeTextChannel channel, String contentRaw) {
        this.channel = channel;
        this.contentRaw = contentRaw;
    }

    public FakeMessage(FakeTextChannel channel, List<MessageEmbed> messageEmbeds) {
        this.channel = channel;
        this.messageEmbeds = messageEmbeds;
    }

    public FakeTextChannel getFakeChannel() {
        return channel;
    }

    /**
     * Add a {@link User} that was mentioned in this message.
     *
     * @param users mentioned {@link User}
     */
    public void addMentionedUsers(User... users) {
        mentionedUsers.addAll(Arrays.asList(users));
    }

    /**
     * Add a {@link Member} that was mentioned in this message.
     *
     * @param members mentioned {@link Member}
     */
    public void addMentionedMembers(Member... members) {
        mentionedMembers.addAll(Arrays.asList(members));
    }

    /**
     * Add a {@link Role} that was mentioned in this message.
     *
     * @param roles mentioned {@link Role}
     */
    public void addMentionedRoles(Role... roles) {
        mentionedRoles.addAll(Arrays.asList(roles));
    }

    /**
     * Add a {@link TextChannel} that was mentioned in this message.
     *
     * @param channels mentioned {@link TextChannel}
     */
    public void addMentionedChannel(TextChannel... channels) {
        mentionedChannels.addAll(Arrays.asList(channels));
    }

    /**
     * Set the {@link User} that will be returned at {@link FakeMessage#getAuthor()}.
     *
     * @param user {@link FakeUser} that will be returned at {@link FakeMessage#getAuthor()}.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Set the {@link Member} that will be returned at {@link FakeMessage#getMember()}.
     *
     * @param member {@link FakeMember} that will be returned at {@link FakeMessage#getMember()}.
     */
    public void setMember(Member member) {
        this.member = member;
    }

    @Nullable
    @Override
    public Message getReferencedMessage() {
        return null;
    }

    
    @Override
    public List<User> getMentionedUsers() {
        return mentionedUsers;
    }

    
    @Override
    public Bag<User> getMentionedUsersBag() {
        return null;
    }

    
    @Override
    public List<TextChannel> getMentionedChannels() {
        return mentionedChannels;
    }

    
    @Override
    public Bag<TextChannel> getMentionedChannelsBag() {
        return null;
    }

    
    @Override
    public List<Role> getMentionedRoles() {
        return mentionedRoles;
    }

    
    @Override
    public Bag<Role> getMentionedRolesBag() {
        return null;
    }

    
    @Override
    public List<Member> getMentionedMembers(Guild guild) {
        return mentionedMembers;
    }

    
    @Override
    public List<Member> getMentionedMembers() {
        return mentionedMembers;
    }

    
    @Override
    public List<IMentionable> getMentions(MentionType... types) {
        return null;
    }

    @Override
    public boolean isMentioned(IMentionable mentionable,  MentionType... types) {
        return false;
    }

    @Override
    public boolean mentionsEveryone() {
        return false;
    }

    @Override
    public boolean isEdited() {
        return false;
    }

    @Nullable
    @Override
    public OffsetDateTime getTimeEdited() {
        return null;
    }

    
    @Override
    public User getAuthor() {
        return user;
    }

    @Nullable
    @Override
    public Member getMember() {
        return member;
    }

    
    @Override
    public String getJumpUrl() {
        return null;
    }

    
    @Override
    public String getContentDisplay() {
        //TODO: Get displayed content of raw content
        return contentRaw;
    }

    
    @Override
    public String getContentRaw() {
        return contentRaw;
    }

    
    @Override
    public String getContentStripped() {
        //TODO: Strip content raw
        return contentRaw;
    }

    
    @Override
    public List<String> getInvites() {
        return null;
    }

    @Nullable
    @Override
    public String getNonce() {
        return null;
    }

    @Override
    public boolean isFromType(ChannelType type) {
        return false;
    }

    
    @Override
    public ChannelType getChannelType() {
        return ChannelType.TEXT;
    }

    @Override
    public boolean isWebhookMessage() {
        return false;
    }

    
    @Override
    public MessageChannel getChannel() {
        return channel;
    }

    
    @Override
    public PrivateChannel getPrivateChannel() {
        return null;
    }

    
    @Override
    public TextChannel getTextChannel() {
        return channel;
    }

    @Nullable
    @Override
    public Category getCategory() {
        return null;
    }

    
    @Override
    public Guild getGuild() {
        return channel.getGuild();
    }

    
    @Override
    public List<Attachment> getAttachments() {
        return null;
    }

    
    @Override
    public List<MessageEmbed> getEmbeds() {
        return messageEmbeds;
    }

    
    @Override
    public List<ActionRow> getActionRows() {
        return null;
    }

    
    @Override
    public List<Emote> getEmotes() {
        return null;
    }

    
    @Override
    public Bag<Emote> getEmotesBag() {
        return null;
    }

    
    @Override
    public List<MessageReaction> getReactions() {
        return new ArrayList<>();
    }

    
    @Override
    public List<MessageSticker> getStickers() {
        return new ArrayList<>();
    }

    @Override
    public boolean isTTS() {
        return false;
    }

    @Nullable
    @Override
    public MessageActivity getActivity() {
        return null;
    }

    
    @Override
    public MessageAction editMessage(CharSequence newContent) {
        return null;
    }

    
    @Override
    public MessageAction editMessageEmbeds(Collection<? extends MessageEmbed> embeds) {
        return null;
    }

    
    @Override
    public MessageAction editMessageFormat(String format,  Object... args) {
        return null;
    }

    
    @Override
    public MessageAction editMessage(Message newContent) {
        return null;
    }

    
    @Override
    public AuditableRestAction<Void> delete() {
        return null;
    }

    
    @Override
    public JDA getJDA() {
        return channel.getJDA();
    }

    @Override
    public boolean isPinned() {
        return false;
    }

    
    @Override
    public RestAction<Void> pin() {
        return null;
    }

    
    @Override
    public RestAction<Void> unpin() {
        return null;
    }

    
    @Override
    public RestAction<Void> addReaction(Emote emote) {
        return null;
    }

    
    @Override
    public RestAction<Void> addReaction(String unicode) {
        return null;
    }

    
    @Override
    public RestAction<Void> clearReactions() {
        return null;
    }

    
    @Override
    public RestAction<Void> clearReactions(String unicode) {
        return null;
    }

    
    @Override
    public RestAction<Void> clearReactions(Emote emote) {
        return null;
    }

    
    @Override
    public RestAction<Void> removeReaction(Emote emote) {
        return null;
    }

    
    @Override
    public RestAction<Void> removeReaction(Emote emote,  User user) {
        return null;
    }

    
    @Override
    public RestAction<Void> removeReaction(String unicode) {
        return null;
    }

    
    @Override
    public RestAction<Void> removeReaction(String unicode,  User user) {
        return null;
    }

    
    @Override
    public ReactionPaginationAction retrieveReactionUsers(Emote emote) {
        return null;
    }

    
    @Override
    public ReactionPaginationAction retrieveReactionUsers(String unicode) {
        return null;
    }

    @Nullable
    @Override
    public MessageReaction.ReactionEmote getReactionByUnicode(String unicode) {
        return null;
    }

    @Nullable
    @Override
    public MessageReaction.ReactionEmote getReactionById(String id) {
        return null;
    }

    @Nullable
    @Override
    public MessageReaction.ReactionEmote getReactionById(long id) {
        return null;
    }

    
    @Override
    public AuditableRestAction<Void> suppressEmbeds(boolean suppressed) {
        return null;
    }

    
    @Override
    public RestAction<Message> crosspost() {
        return null;
    }

    @Override
    public boolean isSuppressedEmbeds() {
        return false;
    }

    
    @Override
    public EnumSet<MessageFlag> getFlags() {
        return null;
    }

    
    @Override
    public MessageType getType() {
        return null;
    }

    @Override
    public void formatTo(Formatter formatter, int flags, int width, int precision) { }

    @Override
    public long getIdLong() {
        return 0;
    }

}
