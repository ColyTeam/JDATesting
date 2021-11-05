package dev.coly.jdat.entities;

import dev.coly.jdat.JDAObjects;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.internal.JDAImpl;
import net.dv8tion.jda.internal.utils.config.AuthorizationConfig;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class FakeJDAImpl extends JDAImpl {

    public FakeJDAImpl(AuthorizationConfig authConfig) {
        super(authConfig);
    }

    @NotNull
    @Override
    public SelfUser getSelfUser() {
        return JDAObjects.getFakeSelfUser();
    }

    @NotNull
    @Override
    public ScheduledExecutorService getGatewayPool() {
        return new ScheduledThreadPoolExecutor(1);
    }
}
