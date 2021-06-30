package dev.coly.jdat.entities.events;

import dev.coly.jdat.entities.FakeMessage;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class FakeGuildMessageReceivedEvent extends GuildMessageReceivedEvent {

    private final FakeMessage message;

    public FakeGuildMessageReceivedEvent(JDA api, FakeMessage message) {
        super(api, 1, message);
        this.message = message;
    }

    /**
     * Wait for the output of the command.
     *
     * @return                      The output of the command.
     * @throws InterruptedException Uses await.
     */
    public Message awaitReturn() throws InterruptedException {
        return message.getFakeChannel().awaitReturn();
    }

}
