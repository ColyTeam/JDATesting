package dev.coly.jdat.entities.actions;

import dev.coly.jdat.JDAObjects;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyAction;
import net.dv8tion.jda.api.utils.concurrent.DelayedCompletableFuture;
import net.dv8tion.jda.internal.interactions.InteractionHookImpl;
import net.dv8tion.jda.internal.requests.restaction.interactions.ReplyActionImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class FakeReplyAction extends ReplyActionImpl {

    private Message message;
    private boolean defer;
    private final CountDownLatch latch = new CountDownLatch(1);
    private final List<MessageEmbed> list = new LinkedList<>();

    public FakeReplyAction(InteractionHookImpl hook, Message message) {
        super(hook);
        this.message = message;
    }

    public Message getMessage() {
        if (message == null)
            this.message = JDAObjects.getFakeMessage(list);

        return message;
    }

    public ReplyAction setDefer(boolean defer) {
        this.defer = defer;
        return this;
    }

    @NotNull
    @Override
    public ReplyAction addEmbeds(@NotNull MessageEmbed... embeds) {
        super.addEmbeds(embeds);
        list.addAll(List.of(embeds));
        return this;
    }

    @NotNull
    @Override
    public ReplyAction addEmbeds(@NotNull Collection<? extends MessageEmbed> embeds) {
        super.addEmbeds(embeds);
        list.addAll(embeds);
        return this;
    }

    public boolean isDefer() {
        return defer;
    }

    @Override
    public void queue() {
       latch.countDown();
    }

    @Override
    public void queue(@Nullable Consumer<? super InteractionHook> success) {
        latch.countDown();
    }

    @Override
    public void queue(Consumer<? super InteractionHook> success, Consumer<? super Throwable> failure) {
        latch.countDown();
    }

    /**
     * Currently not correct implemented. This will not return a {@link ScheduledFuture}.
     */
    @NotNull
    @Override
    public ScheduledFuture<?> queueAfter(long delay, @NotNull TimeUnit unit) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                latch.countDown();
            }
        }, unit.toMillis(delay));
        return null;
    }

    /**
     * Currently not correct implemented. This will not return a {@link ScheduledFuture}.
     */
    @NotNull
    @Override
    public ScheduledFuture<?> queueAfter(long delay, @NotNull TimeUnit unit, @Nullable Consumer<? super InteractionHook> success) {
        return queueAfter(delay, unit);
    }

    /**
     * Currently not correct implemented. This will not return a {@link ScheduledFuture}.
     */
    @NotNull
    @Override
    public ScheduledFuture<?> queueAfter(long delay, @NotNull TimeUnit unit, @Nullable ScheduledExecutorService executor) {
        return queueAfter(delay, unit);
    }

    /**
     * Currently not correct implemented. This will not return a {@link ScheduledFuture}.
     */
    @NotNull
    @Override
    public ScheduledFuture<?> queueAfter(long delay, @NotNull TimeUnit unit, @Nullable Consumer<? super InteractionHook> success, @Nullable Consumer<? super Throwable> failure) {
        return queueAfter(delay, unit);
    }

    /**
     * Currently not correct implemented. This will not return a {@link ScheduledFuture}.
     */
    @NotNull
    @Override
    public ScheduledFuture<?> queueAfter(long delay, @NotNull TimeUnit unit, @Nullable Consumer<? super InteractionHook> success, @Nullable ScheduledExecutorService executor) {
        return queueAfter(delay, unit);
    }

    /**
     * Currently not correct implemented. This will not return a {@link ScheduledFuture}.
     */
    @NotNull
    @Override
    public ScheduledFuture<?> queueAfter(long delay, @NotNull TimeUnit unit, @Nullable Consumer<? super InteractionHook> success, @Nullable Consumer<? super Throwable> failure, @Nullable ScheduledExecutorService executor) {
        return queueAfter(delay, unit);
    }

    /**
     * Currently not correct implemented. This will not return a {@link CompletableFuture}.
     */
    @NotNull
    @Override
    public CompletableFuture<InteractionHook> submit() {
        latch.countDown();
        return null;
    }

    /**
     * Currently not correct implemented. This will not return a {@link CompletableFuture}.
     */
    @NotNull
    @Override
    public CompletableFuture<InteractionHook> submit(boolean shouldQueue) {
        latch.countDown();
        return null;
    }

    /**
     * Currently not correct implemented. This will not return a {@link DelayedCompletableFuture}.
     */
    @NotNull
    @Override
    public DelayedCompletableFuture<InteractionHook> submitAfter(long delay, @NotNull TimeUnit unit) {
        queueAfter(delay, unit);
        return null;
    }

    /**
     * Currently not correct implemented. This will not return a {@link DelayedCompletableFuture}.
     */
    @NotNull
    @Override
    public DelayedCompletableFuture<InteractionHook> submitAfter(long delay, @NotNull TimeUnit unit, @Nullable ScheduledExecutorService executor) {
        queueAfter(delay, unit);
        return null;
    }

    public void awaitReturn() throws InterruptedException {
        latch.await();
    }
}
