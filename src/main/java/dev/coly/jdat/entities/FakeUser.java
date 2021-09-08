package dev.coly.jdat.entities;

import dev.coly.jdat.JDAObjects;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.requests.RestAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

public class FakeUser implements User {

    
    @Override
    @NotNull
    public String getName() {
        return "User";
    }
    
    @Override
    @NotNull
    public String getDiscriminator() {
        return "0000";
    }

    @Nullable
    @Override
    public String getAvatarId() {
        return "0";
    }

    
    @Override
    @NotNull
    public String getDefaultAvatarId() {
        return "0";
    }

    
    @Override
    @NotNull
    public String getAsTag() {
        return getName() + '#' + getDiscriminator();
    }

    @Override
    public boolean hasPrivateChannel() {
        return false;
    }

    
    @Override
    public RestAction<PrivateChannel> openPrivateChannel() {
        return null;
    }

    
    @Override
    @NotNull
    public List<Guild> getMutualGuilds() {
        return Collections.singletonList(JDAObjects.getFakeGuild());
    }

    @Override
    public boolean isBot() {
        return false;
    }

    @Override
    public boolean isSystem() {
        return false;
    }

    
    @Override
    @NotNull
    public JDA getJDA() {
        return JDAObjects.getFakeJDA();
    }

    
    @Override
    @NotNull
    public EnumSet<UserFlag> getFlags() {
        return (EnumSet<UserFlag>) Collections.emptyEnumeration();
    }

    @Override
    public int getFlagsRaw() {
        return 0;
    }

    
    @Override
    @NotNull
    public String getAsMention() {
        return "<@" + getId() + ">";
    }

    @Override
    public long getIdLong() {
        return 0;
    }

}
