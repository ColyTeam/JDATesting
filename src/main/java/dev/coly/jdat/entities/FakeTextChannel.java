package dev.coly.jdat.entities;

import dev.coly.jdat.JDAObjects;
import dev.coly.jdat.entities.actions.FakeMessageAction;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.ChannelManager;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public class FakeTextChannel implements TextChannel {

    private final Guild guild;
    private final String name;
    private String topic;

    private FakeMessageAction messageAction;

    public FakeTextChannel(Guild guild, String name) {
        this.guild = guild;
        this.name = name;
    }

    /**
     * Set topic of channel. This will be returned at {@link FakeTextChannel#getTopic()}.
     *
     * @param topic topic of channel
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }

    
    @Override
    public MessageAction sendMessage(Message msg) {
        messageAction = new FakeMessageAction(msg);
        return messageAction;
    }

    @Override
    public MessageAction sendMessage(CharSequence text) {
        return sendMessage(JDAObjects.getFakeMessage(this, text.toString()));
    }

    @NotNull
    @Override
    public MessageAction sendMessage(@NotNull MessageEmbed embed) {
        return sendMessage(JDAObjects.getFakeMessage(this, embed));
    }

    public Message awaitReturn() throws InterruptedException {
        return messageAction.awaitReturn();
    }

    @Nullable
    @Override
    public String getTopic() {
        return topic;
    }

    @Override
    public boolean isNSFW() {
        return false;
    }

    @Override
    public boolean isNews() {
        return false;
    }

    @Override
    public int getSlowmode() {
        return 0;
    }

    
    @Override
    public Guild getGuild() {
        return guild;
    }

    @Nullable
    @Override
    public Category getParent() {
        return null;
    }

    
    @Override
    public List<Member> getMembers() {
        return null;
    }

    @Override
    public int getPosition() {
        return 0;
    }

    @Override
    public int getPositionRaw() {
        return 0;
    }

    @Nullable
    @Override
    public PermissionOverride getPermissionOverride(IPermissionHolder permissionHolder) {
        return null;
    }

    
    @Override
    public List<PermissionOverride> getPermissionOverrides() {
        return null;
    }

    
    @Override
    public List<PermissionOverride> getMemberPermissionOverrides() {
        return null;
    }

    
    @Override
    public List<PermissionOverride> getRolePermissionOverrides() {
        return null;
    }

    @Override
    public boolean isSynced() {
        return false;
    }

    
    @Override
    public ChannelAction<TextChannel> createCopy(Guild guild) {
        return null;
    }

    
    @Override
    public ChannelAction<TextChannel> createCopy() {
        return null;
    }

    
    @Override
    public ChannelManager getManager() {
        return null;
    }

    
    @Override
    public AuditableRestAction<Void> delete() {
        return null;
    }

    
    @Override
    public PermissionOverrideAction createPermissionOverride(IPermissionHolder permissionHolder) {
        return null;
    }

    
    @Override
    public PermissionOverrideAction putPermissionOverride(IPermissionHolder permissionHolder) {
        return null;
    }

    
    @Override
    public InviteAction createInvite() {
        return null;
    }

    
    @Override
    public RestAction<List<Invite>> retrieveInvites() {
        return null;
    }

    
    @Override
    public RestAction<List<Webhook>> retrieveWebhooks() {
        return null;
    }

    
    @Override
    public WebhookAction createWebhook(String name) {
        return null;
    }

    
    @Override
    public RestAction<Webhook.WebhookReference> follow(String targetChannelId) {
        return null;
    }

    
    @Override
    public RestAction<Void> deleteMessages(Collection<Message> messages) {
        return null;
    }

    
    @Override
    public RestAction<Void> deleteMessagesByIds(Collection<String> messageIds) {
        return null;
    }

    
    @Override
    public AuditableRestAction<Void> deleteWebhookById(String id) {
        return null;
    }

    
    @Override
    public RestAction<Void> clearReactionsById(String messageId) {
        return null;
    }

    
    @Override
    public RestAction<Void> clearReactionsById(String messageId,  String unicode) {
        return null;
    }

    
    @Override
    public RestAction<Void> clearReactionsById(String messageId,  Emote emote) {
        return null;
    }

    
    @Override
    public RestAction<Void> removeReactionById(String messageId,  String unicode,  User user) {
        return null;
    }

    @Override
    public boolean canTalk() {
        return false;
    }

    @Override
    public boolean canTalk(Member member) {
        return false;
    }

    @Override
    public int compareTo(GuildChannel o) {
        return 0;
    }

    @Override
    public long getLatestMessageIdLong() {
        return 0;
    }

    @Override
    public boolean hasLatestMessage() {
        return false;
    }

    
    @Override
    public String getName() {
        return name;
    }

    
    @Override
    public ChannelType getType() {
        return ChannelType.TEXT;
    }

    
    @Override
    public JDA getJDA() {
        return guild.getJDA();
    }

    
    @Override
    public String getAsMention() {
        return null;
    }

    @Override
    public long getIdLong() {
        return 0;
    }
}
