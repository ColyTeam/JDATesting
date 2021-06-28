package dev.coly.jdat.entities;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

public class FakeMember implements Member {

    
    @Override
    public User getUser() {
        return null;
    }

    
    @Override
    public Guild getGuild() {
        return null;
    }

    
    @Override
    public EnumSet<Permission> getPermissions() {
        return null;
    }

    
    @Override
    public EnumSet<Permission> getPermissions(GuildChannel guildChannel) {
        return null;
    }

    
    @Override
    public EnumSet<Permission> getPermissionsExplicit() {
        return null;
    }

    
    @Override
    public EnumSet<Permission> getPermissionsExplicit(GuildChannel guildChannel) {
        return null;
    }

    @Override
    public boolean hasPermission(Permission... permissions) {
        return false;
    }

    @Override
    public boolean hasPermission(Collection<Permission> collection) {
        return false;
    }

    @Override
    public boolean hasPermission(GuildChannel guildChannel,  Permission... permissions) {
        return false;
    }

    @Override
    public boolean hasPermission(GuildChannel guildChannel,  Collection<Permission> collection) {
        return false;
    }

    @Override
    public boolean canSync(GuildChannel guildChannel,  GuildChannel guildChannel1) {
        return false;
    }

    @Override
    public boolean canSync(GuildChannel guildChannel) {
        return false;
    }

    
    @Override
    public JDA getJDA() {
        return null;
    }

    
    @Override
    public OffsetDateTime getTimeJoined() {
        return null;
    }

    @Override
    public boolean hasTimeJoined() {
        return false;
    }

    @Nullable
    @Override
    public OffsetDateTime getTimeBoosted() {
        return null;
    }

    @Nullable
    @Override
    public GuildVoiceState getVoiceState() {
        return null;
    }

    
    @Override
    public List<Activity> getActivities() {
        return null;
    }

    
    @Override
    public OnlineStatus getOnlineStatus() {
        return null;
    }

    
    @Override
    public OnlineStatus getOnlineStatus(ClientType clientType) {
        return null;
    }

    
    @Override
    public EnumSet<ClientType> getActiveClients() {
        return null;
    }

    @Nullable
    @Override
    public String getNickname() {
        return null;
    }

    
    @Override
    public String getEffectiveName() {
        return null;
    }

    
    @Override
    public List<Role> getRoles() {
        return null;
    }

    @Nullable
    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public int getColorRaw() {
        return 0;
    }

    @Override
    public boolean canInteract(Member member) {
        return false;
    }

    @Override
    public boolean canInteract(Role role) {
        return false;
    }

    @Override
    public boolean canInteract(Emote emote) {
        return false;
    }

    @Override
    public boolean isOwner() {
        return false;
    }

    @Override
    public boolean isPending() {
        return false;
    }

    @Nullable
    @Override
    public TextChannel getDefaultChannel() {
        return null;
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
