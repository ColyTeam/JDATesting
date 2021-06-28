package dev.coly.jdat.entities;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Region;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.templates.Template;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.privileges.CommandPrivilege;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.managers.GuildManager;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.*;
import net.dv8tion.jda.api.requests.restaction.order.CategoryOrderAction;
import net.dv8tion.jda.api.requests.restaction.order.ChannelOrderAction;
import net.dv8tion.jda.api.requests.restaction.order.RoleOrderAction;
import net.dv8tion.jda.api.requests.restaction.pagination.AuditLogPaginationAction;
import net.dv8tion.jda.api.utils.cache.MemberCacheView;
import net.dv8tion.jda.api.utils.cache.SnowflakeCacheView;
import net.dv8tion.jda.api.utils.cache.SortedSnowflakeCacheView;
import net.dv8tion.jda.api.utils.concurrent.Task;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class FakeGuild implements Guild {

    private final JDA jda;
    private final String name;

    public FakeGuild(JDA jda, String name) {
        this.name = name;
        this.jda = jda;
    }
    
    @Override
    public RestAction<List<Command>> retrieveCommands() {
        return null;
    }
    
    @Override
    public RestAction<Command> retrieveCommandById(String s) {
        return null;
    }
    
    @Override
    public CommandCreateAction upsertCommand(CommandData commandData) {
        return null;
    }
    
    @Override
    public CommandListUpdateAction updateCommands() {
        return null;
    }
    
    @Override
    public CommandEditAction editCommandById(String s) {
        return null;
    }

    
    @Override
    public RestAction<Void> deleteCommandById(String s) {
        return null;
    }

    
    @Override
    public RestAction<List<CommandPrivilege>> retrieveCommandPrivilegesById(String s) {
        return null;
    }

    
    @Override
    public RestAction<Map<String, List<CommandPrivilege>>> retrieveCommandPrivileges() {
        return null;
    }

    
    @Override
    public RestAction<List<CommandPrivilege>> updateCommandPrivilegesById(String s,  Collection<? extends CommandPrivilege> collection) {
        return null;
    }

    
    @Override
    public RestAction<Map<String, List<CommandPrivilege>>> updateCommandPrivileges(Map<String, Collection<? extends CommandPrivilege>> map) {
        return null;
    }

    
    @Override
    public RestAction<EnumSet<Region>> retrieveRegions(boolean b) {
        return null;
    }

    
    @Override
    public MemberAction addMember(String s,  String s1) {
        return null;
    }

    @Override
    public boolean isLoaded() {
        return false;
    }

    @Override
    public void pruneMemberCache() { }

    @Override
    public boolean unloadMember(long l) {
        return false;
    }

    @Override
    public int getMemberCount() {
        return 0;
    }

    
    @Override
    public String getName() {
        return name;
    }

    @Nullable
    @Override
    public String getIconId() {
        return null;
    }

    
    @Override
    public Set<String> getFeatures() {
        return null;
    }

    @Nullable
    @Override
    public String getSplashId() {
        return null;
    }

    
    @Override
    public RestAction<String> retrieveVanityUrl() {
        return null;
    }

    @Nullable
    @Override
    public String getVanityCode() {
        return null;
    }

    
    @Override
    public RestAction<VanityInvite> retrieveVanityInvite() {
        return null;
    }

    @Nullable
    @Override
    public String getDescription() {
        return null;
    }

    
    @Override
    public Locale getLocale() {
        return null;
    }

    @Nullable
    @Override
    public String getBannerId() {
        return null;
    }

    
    @Override
    public BoostTier getBoostTier() {
        return null;
    }

    @Override
    public int getBoostCount() {
        return 0;
    }

    
    @Override
    public List<Member> getBoosters() {
        return null;
    }

    @Override
    public int getMaxMembers() {
        return 0;
    }

    @Override
    public int getMaxPresences() {
        return 0;
    }

    
    @Override
    public RestAction<MetaData> retrieveMetaData() {
        return null;
    }

    @Nullable
    @Override
    public VoiceChannel getAfkChannel() {
        return null;
    }

    @Nullable
    @Override
    public TextChannel getSystemChannel() {
        return null;
    }

    @Nullable
    @Override
    public TextChannel getRulesChannel() {
        return null;
    }

    @Nullable
    @Override
    public TextChannel getCommunityUpdatesChannel() {
        return null;
    }

    @Nullable
    @Override
    public Member getOwner() {
        return null;
    }

    @Override
    public long getOwnerIdLong() {
        return 0;
    }

    
    @Override
    public Timeout getAfkTimeout() {
        return null;
    }

    
    @Override
    public String getRegionRaw() {
        return null;
    }

    @Override
    public boolean isMember(User user) {
        return false;
    }

    
    @Override
    public Member getSelfMember() {
        return null;
    }

    @Nullable
    @Override
    public Member getMember(User user) {
        return null;
    }

    
    @Override
    public MemberCacheView getMemberCache() {
        return null;
    }

    
    @Override
    public SortedSnowflakeCacheView<Category> getCategoryCache() {
        return null;
    }

    
    @Override
    public SortedSnowflakeCacheView<StoreChannel> getStoreChannelCache() {
        return null;
    }

    
    @Override
    public SortedSnowflakeCacheView<TextChannel> getTextChannelCache() {
        return null;
    }

    
    @Override
    public SortedSnowflakeCacheView<VoiceChannel> getVoiceChannelCache() {
        return null;
    }

    
    @Override
    public List<GuildChannel> getChannels(boolean b) {
        return null;
    }

    
    @Override
    public SortedSnowflakeCacheView<Role> getRoleCache() {
        return null;
    }

    
    @Override
    public SnowflakeCacheView<Emote> getEmoteCache() {
        return null;
    }

    
    @Override
    public RestAction<List<ListedEmote>> retrieveEmotes() {
        return null;
    }

    
    @Override
    public RestAction<ListedEmote> retrieveEmoteById(String s) {
        return null;
    }

    
    @Override
    public RestAction<List<Ban>> retrieveBanList() {
        return null;
    }

    
    @Override
    public RestAction<Ban> retrieveBanById(String s) {
        return null;
    }

    
    @Override
    public RestAction<Integer> retrievePrunableMemberCount(int i) {
        return null;
    }

    
    @Override
    public Role getPublicRole() {
        return null;
    }

    @Nullable
    @Override
    public TextChannel getDefaultChannel() {
        return null;
    }

    
    @Override
    public GuildManager getManager() {
        return null;
    }

    
    @Override
    public AuditLogPaginationAction retrieveAuditLogs() {
        return null;
    }

    
    @Override
    public RestAction<Void> leave() {
        return null;
    }

    
    @Override
    public RestAction<Void> delete() {
        return null;
    }

    
    @Override
    public RestAction<Void> delete(@Nullable String s) {
        return null;
    }

    
    @Override
    public AudioManager getAudioManager() {
        return null;
    }
    
    @Override
    public JDA getJDA() {
        return null;
    }

    
    @Override
    public RestAction<List<Invite>> retrieveInvites() {
        return null;
    }

    
    @Override
    public RestAction<List<Template>> retrieveTemplates() {
        return null;
    }

    
    @Override
    public RestAction<Template> createTemplate(String s, @Nullable String s1) {
        return null;
    }

    
    @Override
    public RestAction<List<Webhook>> retrieveWebhooks() {
        return null;
    }

    
    @Override
    public List<GuildVoiceState> getVoiceStates() {
        return null;
    }

    
    @Override
    public VerificationLevel getVerificationLevel() {
        return null;
    }

    
    @Override
    public NotificationLevel getDefaultNotificationLevel() {
        return null;
    }

    
    @Override
    public MFALevel getRequiredMFALevel() {
        return null;
    }

    
    @Override
    public ExplicitContentLevel getExplicitContentLevel() {
        return null;
    }

    @Override
    public boolean checkVerification() {
        return false;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    
    @Override
    public CompletableFuture<Void> retrieveMembers() {
        return null;
    }

    
    @Override
    public Task<Void> loadMembers(Consumer<Member> consumer) {
        return null;
    }

    
    @Override
    public RestAction<Member> retrieveMemberById(long l, boolean b) {
        return null;
    }

    
    @Override
    public Task<List<Member>> retrieveMembersByIds(boolean b,  long... longs) {
        return null;
    }

    
    @Override
    public Task<List<Member>> retrieveMembersByPrefix(String s, int i) {
        return null;
    }

    
    @Override
    public RestAction<Void> moveVoiceMember(Member member, @Nullable VoiceChannel voiceChannel) {
        return null;
    }

    
    @Override
    public AuditableRestAction<Void> modifyNickname(Member member, @Nullable String s) {
        return null;
    }

    
    @Override
    public AuditableRestAction<Integer> prune(int i, boolean b,  Role... roles) {
        return null;
    }

    
    @Override
    public AuditableRestAction<Void> kick(Member member, @Nullable String s) {
        return null;
    }

    
    @Override
    public AuditableRestAction<Void> kick(String s, @Nullable String s1) {
        return null;
    }

    
    @Override
    public AuditableRestAction<Void> ban(User user, int i, @Nullable String s) {
        return null;
    }

    
    @Override
    public AuditableRestAction<Void> ban(String s, int i, @Nullable String s1) {
        return null;
    }

    
    @Override
    public AuditableRestAction<Void> unban(String s) {
        return null;
    }

    
    @Override
    public AuditableRestAction<Void> deafen(Member member, boolean b) {
        return null;
    }

    
    @Override
    public AuditableRestAction<Void> mute(Member member, boolean b) {
        return null;
    }

    
    @Override
    public AuditableRestAction<Void> addRoleToMember(Member member,  Role role) {
        return null;
    }

    
    @Override
    public AuditableRestAction<Void> removeRoleFromMember(Member member,  Role role) {
        return null;
    }

    
    @Override
    public AuditableRestAction<Void> modifyMemberRoles(Member member, @Nullable Collection<Role> collection, @Nullable Collection<Role> collection1) {
        return null;
    }

    
    @Override
    public AuditableRestAction<Void> modifyMemberRoles(Member member,  Collection<Role> collection) {
        return null;
    }

    
    @Override
    public AuditableRestAction<Void> transferOwnership(Member member) {
        return null;
    }

    
    @Override
    public ChannelAction<TextChannel> createTextChannel(String s, @Nullable Category category) {
        return null;
    }

    
    @Override
    public ChannelAction<VoiceChannel> createVoiceChannel(String s, @Nullable Category category) {
        return null;
    }

    
    @Override
    public ChannelAction<Category> createCategory(String s) {
        return null;
    }

    
    @Override
    public RoleAction createRole() {
        return null;
    }

    
    @Override
    public AuditableRestAction<Emote> createEmote(String s,  Icon icon,  Role... roles) {
        return null;
    }

    
    @Override
    public ChannelOrderAction modifyCategoryPositions() {
        return null;
    }

    
    @Override
    public ChannelOrderAction modifyTextChannelPositions() {
        return null;
    }

    
    @Override
    public ChannelOrderAction modifyVoiceChannelPositions() {
        return null;
    }

    
    @Override
    public CategoryOrderAction modifyTextChannelPositions(Category category) {
        return null;
    }

    
    @Override
    public CategoryOrderAction modifyVoiceChannelPositions(Category category) {
        return null;
    }

    
    @Override
    public RoleOrderAction modifyRolePositions(boolean b) {
        return null;
    }

    @Override
    public long getIdLong() {
        return 0;
    }

}
