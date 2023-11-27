package dev.coly.jdat;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;

public class TestSubscribeEvent {

    @SubscribeEvent
    public void subscribeMessage(MessageReceivedEvent e) {
        TestEventListener.handleMessageReceived(e);
    }

}
