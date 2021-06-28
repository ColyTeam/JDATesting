package dev.coly.jdat.entities;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.hooks.IEventManager;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.managers.DirectAudioController;
import net.dv8tion.jda.api.managers.Presence;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;
import net.dv8tion.jda.api.requests.restaction.CommandEditAction;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.dv8tion.jda.api.requests.restaction.GuildAction;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.dv8tion.jda.api.utils.cache.CacheView;
import net.dv8tion.jda.api.utils.cache.SnowflakeCacheView;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class FakeJDA implements JDA {

    List<GatewayIntent> gatewayIntents = new ArrayList<>();
    List<CacheFlag> cacheFlags = new ArrayList<>();

    public FakeJDA(List<GatewayIntent> gatewayIntents, List<CacheFlag> cacheFlags) {
        this.gatewayIntents = gatewayIntents;
        this.cacheFlags = cacheFlags;
    }

    public FakeJDA() { }

    
    @Override
    public Status getStatus() {
        return Status.CONNECTED;
    }

    
    @Override
    public EnumSet<GatewayIntent> getGatewayIntents() {
        if (gatewayIntents.size() == 0) {
            return EnumSet.noneOf(GatewayIntent.class);
        } else {
            GatewayIntent first = gatewayIntents.get(0);
            gatewayIntents.remove(first);
            if (gatewayIntents.size() == 0) {
                return EnumSet.of(first);
            } else {
                return EnumSet.of(first, gatewayIntents.toArray(new GatewayIntent[0]));
            }
        }
    }

    
    @Override
    public EnumSet<CacheFlag> getCacheFlags() {
        if (cacheFlags.size() == 0) {
            return EnumSet.noneOf(CacheFlag.class);
        } else {
            CacheFlag first = cacheFlags.get(0);
            cacheFlags.remove(first);
            if (cacheFlags.size() == 0) {
                return EnumSet.of(first);
            } else {
                return EnumSet.of(first, cacheFlags.toArray(new CacheFlag[0]));
            }
        }
    }

    @Override
    public boolean unloadUser(long userId) {
        return true;
    }

    @Override
    public long getGatewayPing() {
        return 0;
    }

    
    @Override
    public JDA awaitStatus(JDA.Status status,  Status... failOn) throws InterruptedException {
        return this;
    }

    @Override
    public int cancelRequests() {
        return 0;
    }

    
    @Override
    public ScheduledExecutorService getRateLimitPool() {
        return Executors.newScheduledThreadPool(1);
    }

    
    @Override
    public ScheduledExecutorService getGatewayPool() {
        return Executors.newScheduledThreadPool(1);
    }

    
    @Override
    public ExecutorService getCallbackPool() {
        return Executors.newScheduledThreadPool(1);
    }

    
    @Override
    public OkHttpClient getHttpClient() {
        return new OkHttpClient();
    }

    /**
     * Will not be implemented because typically  not in use.
     */
    
    @Override
    public DirectAudioController getDirectAudioController() {
        return null;
    }

    @Override
    public void setEventManager(@Nullable IEventManager manager) { }

    /**
     * Not use this method. Create a {@link FakeEvent} instead.
     */
    @Override
    public void addEventListener(Object... listeners) { }

    /**
     * Not use this method. Create a {@link FakeEvent} instead.
     */
    @Override
    public void removeEventListener(Object... listeners) { }

    
    @Override
    public List<Object> getRegisteredListeners() {
        return new ArrayList<>();
    }

    
    @Override
    public RestAction<List<Command>> retrieveCommands() {
        return null;
    }

    
    @Override
    public RestAction<Command> retrieveCommandById(String id) {
        return null;
    }

    
    @Override
    public CommandCreateAction upsertCommand(CommandData command) {
        return null;
    }

    
    @Override
    public CommandListUpdateAction updateCommands() {
        return null;
    }

    
    @Override
    public CommandEditAction editCommandById(String id) {
        return null;
    }

    
    @Override
    public RestAction<Void> deleteCommandById(String commandId) {
        return null;
    }

    
    @Override
    public GuildAction createGuild(String name) {
        return null;
    }

    
    @Override
    public RestAction<Void> createGuildFromTemplate(String code,  String name, @Nullable Icon icon) {
        return null;
    }

    
    @Override
    public CacheView<AudioManager> getAudioManagerCache() {
        return null;
    }

    
    @Override
    public SnowflakeCacheView<User> getUserCache() {
        return null;
    }

    
    @Override
    public List<Guild> getMutualGuilds(User... users) {
        return null;
    }

    
    @Override
    public List<Guild> getMutualGuilds(Collection<User> users) {
        return null;
    }

    
    @Override
    public RestAction<User> retrieveUserById(long id, boolean update) {
        return null;
    }

    
    @Override
    public SnowflakeCacheView<Guild> getGuildCache() {
        return null;
    }

    
    @Override
    public Set<String> getUnavailableGuilds() {
        return null;
    }

    @Override
    public boolean isUnavailable(long guildId) {
        return false;
    }

    
    @Override
    public SnowflakeCacheView<Role> getRoleCache() {
        return null;
    }

    
    @Override
    public SnowflakeCacheView<Category> getCategoryCache() {
        return null;
    }

    
    @Override
    public SnowflakeCacheView<StoreChannel> getStoreChannelCache() {
        return null;
    }

    
    @Override
    public SnowflakeCacheView<TextChannel> getTextChannelCache() {
        return null;
    }

    
    @Override
    public SnowflakeCacheView<VoiceChannel> getVoiceChannelCache() {
        return null;
    }

    
    @Override
    public SnowflakeCacheView<PrivateChannel> getPrivateChannelCache() {
        return null;
    }

    
    @Override
    public RestAction<PrivateChannel> openPrivateChannelById(long userId) {
        return null;
    }

    
    @Override
    public SnowflakeCacheView<Emote> getEmoteCache() {
        return null;
    }

    
    @Override
    public IEventManager getEventManager() {
        return null;
    }

    
    @Override
    public SelfUser getSelfUser() {
        return null;
    }

    
    @Override
    public Presence getPresence() {
        return null;
    }

    
    @Override
    public ShardInfo getShardInfo() {
        return null;
    }

    
    @Override
    public String getToken() {
        return null;
    }

    @Override
    public long getResponseTotal() {
        return 0;
    }

    @Override
    public int getMaxReconnectDelay() {
        return 0;
    }

    @Override
    public void setAutoReconnect(boolean reconnect) {

    }

    @Override
    public void setRequestTimeoutRetry(boolean retryOnTimeout) {

    }

    @Override
    public boolean isAutoReconnect() {
        return false;
    }

    @Override
    public boolean isBulkDeleteSplittingEnabled() {
        return false;
    }

    @Override
    public void shutdown() {

    }

    @Override
    public void shutdownNow() {

    }

    
    @Override
    public AccountType getAccountType() {
        return null;
    }

    
    @Override
    public RestAction<ApplicationInfo> retrieveApplicationInfo() {
        return null;
    }

    
    @Override
    public JDA setRequiredScopes(Collection<String> scopes) {
        return null;
    }

    
    @Override
    public String getInviteUrl(@Nullable Permission... permissions) {
        return null;
    }

    
    @Override
    public String getInviteUrl(@Nullable Collection<Permission> permissions) {
        return null;
    }

    @Nullable
    @Override
    public ShardManager getShardManager() {
        return null;
    }

    
    @Override
    public RestAction<Webhook> retrieveWebhookById(String webhookId) {
        return null;
    }

}
