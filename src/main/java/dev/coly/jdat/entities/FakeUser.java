package dev.coly.jdat.entities;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.requests.RestAction;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class FakeUser implements User {

    
    @Override
    public String getName() {
        return null;
    }

    
    @Override
    public String getDiscriminator() {
        return null;
    }

    @Nullable
    @Override
    public String getAvatarId() {
        return null;
    }

    
    @Override
    public String getDefaultAvatarId() {
        return null;
    }

    
    @Override
    public String getAsTag() {
        return null;
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
    public List<Guild> getMutualGuilds() {
        return null;
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
    public JDA getJDA() {
        return null;
    }

    
    @Override
    public EnumSet<UserFlag> getFlags() {
        return null;
    }

    @Override
    public int getFlagsRaw() {
        return 0;
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
