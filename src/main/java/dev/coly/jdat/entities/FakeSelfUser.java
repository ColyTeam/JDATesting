package dev.coly.jdat.entities;

import dev.coly.jdat.JDAObjects;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.managers.AccountManager;
import net.dv8tion.jda.api.requests.RestAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

public class FakeSelfUser implements SelfUser {

    @Override
    public long getApplicationIdLong() {
        return 0;
    }

    @Override
    public boolean isVerified() {
        return false;
    }

    @Override
    public boolean isMfaEnabled() {
        return false;
    }

    @Override
    public long getAllowedFileSize() {
        return 0;
    }

    @NotNull
    @Override
    public AccountManager getManager() {
        return null;
    }

    @NotNull
    @Override
    public String getName() {
        return "User";
    }

    @NotNull
    @Override
    public String getDiscriminator() {
        return "0000";
    }

    @Nullable
    @Override
    public String getAvatarId() {
        return "0";
    }

    @NotNull
    @Override
    public String getDefaultAvatarId() {
        return "0";
    }

    @NotNull
    @Override
    public RestAction<Profile> retrieveProfile() {
        return null;
    }

    @NotNull
    @Override
    public String getAsTag() {
        return "<@0>";
    }

    @Override
    public boolean hasPrivateChannel() {
        return false;
    }

    @NotNull
    @Override
    public RestAction<PrivateChannel> openPrivateChannel() {
        return null;
    }

    @NotNull
    @Override
    public List<Guild> getMutualGuilds() {
        return new LinkedList<>();
    }

    @Override
    public boolean isBot() {
        return false;
    }

    @Override
    public boolean isSystem() {
        return false;
    }

    @NotNull
    @Override
    public JDA getJDA() {
        return JDAObjects.getFakeJDA();
    }

    @NotNull
    @Override
    public EnumSet<UserFlag> getFlags() {
        return (EnumSet<UserFlag>) Collections.emptyEnumeration();
    }

    @Override
    public int getFlagsRaw() {
        return 0;
    }

    @NotNull
    @Override
    public String getAsMention() {
        return "<@" + getId() + ">";
    }

    @Override
    public long getIdLong() {
        return 0;
    }

}
