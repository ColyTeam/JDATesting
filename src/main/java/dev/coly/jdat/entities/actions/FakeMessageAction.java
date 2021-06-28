package dev.coly.jdat.entities.actions;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.IMentionable;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.exceptions.RateLimitedException;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import net.dv8tion.jda.api.utils.AttachmentOption;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public class FakeMessageAction implements MessageAction {

    private Message message;
    private CountDownLatch latch = new CountDownLatch(1);

    public Message awaitReturn() throws InterruptedException {
        latch.await();
        return message;
    }

    public FakeMessageAction(Message message) {
        this.message = message;
    }

    
    @Override
    public JDA getJDA() {
        return null;
    }

    
    @Override
    public MessageAction setCheck(@Nullable BooleanSupplier checks) {
        return null;
    }

    
    @Override
    public MessageAction timeout(long timeout,  TimeUnit unit) {
        return null;
    }

    
    @Override
    public MessageAction deadline(long timestamp) {
        return null;
    }

    @Override
    public void queue(@Nullable Consumer<? super Message> success, @Nullable Consumer<? super Throwable> failure) {
        latch.countDown();
    }

    @Override
    public Message complete(boolean shouldQueue) throws RateLimitedException {
        latch.countDown();
        return message;
    }

    
    @Override
    public CompletableFuture<Message> submit(boolean shouldQueue) {
        return null;
    }

    
    @Override
    public MessageChannel getChannel() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isEdit() {
        return false;
    }

    
    @Override
    public MessageAction apply(@Nullable Message message) {
        return null;
    }

    
    @Override
    public MessageAction referenceById(long messageId) {
        return null;
    }

    
    @Override
    public MessageAction mentionRepliedUser(boolean mention) {
        return null;
    }

    
    @Override
    public MessageAction allowedMentions(@Nullable Collection<Message.MentionType> allowedMentions) {
        return null;
    }

    
    @Override
    public MessageAction mention(IMentionable... mentions) {
        return null;
    }

    
    @Override
    public MessageAction mentionUsers(String... userIds) {
        return null;
    }

    
    @Override
    public MessageAction mentionRoles(String... roleIds) {
        return null;
    }

    
    @Override
    public MessageAction failOnInvalidReply(boolean fail) {
        return null;
    }

    
    @Override
    public MessageAction tts(boolean isTTS) {
        return null;
    }

    
    @Override
    public MessageAction reset() {
        return null;
    }

    
    @Override
    public MessageAction nonce(@Nullable String nonce) {
        return null;
    }

    
    @Override
    public MessageAction content(@Nullable String content) {
        return null;
    }

    
    @Override
    public MessageAction embed(@Nullable MessageEmbed embed) {
        return null;
    }

    
    @Override
    public MessageAction setEmbeds(Collection<? extends MessageEmbed> embeds) {
        return null;
    }

    
    @Override
    public MessageAction append(@Nullable CharSequence csq, int start, int end) {
        return null;
    }

    
    @Override
    public MessageAction append(char c) {
        return null;
    }

    
    @Override
    public MessageAction addFile(InputStream data,  String name,  AttachmentOption... options) {
        return null;
    }

    
    @Override
    public MessageAction addFile(File file,  String name,  AttachmentOption... options) {
        return null;
    }

    
    @Override
    public MessageAction clearFiles() {
        return null;
    }

    
    @Override
    public MessageAction clearFiles(BiConsumer<String, InputStream> finalizer) {
        return null;
    }

    
    @Override
    public MessageAction clearFiles(Consumer<InputStream> finalizer) {
        return null;
    }

    
    @Override
    public MessageAction retainFilesById(Collection<String> ids) {
        return null;
    }

    
    @Override
    public MessageAction setActionRows(ActionRow... rows) {
        return null;
    }

    
    @Override
    public MessageAction override(boolean bool) {
        return null;
    }

}
