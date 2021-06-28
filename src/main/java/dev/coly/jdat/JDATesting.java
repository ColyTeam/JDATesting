package dev.coly.jdat;

import dev.coly.jdat.entities.events.FakeGuildMessageReceivedEvent;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.hooks.EventListener;
import org.junit.jupiter.api.Assertions;

/**
 * This class should be used in testing. It creates fake {@link net.dv8tion.jda.api.events.Event}s and other JDA objects
 * to test your code.
 */
public class JDATesting {

    /**
     * Test a {@link EventListener} with a {@link FakeGuildMessageReceivedEvent}. You can test the return to see if your
     * code returns the correct {@link Message} output for a given input.
     *
     * @param listener              The {@link EventListener} that will be tested.
     * @param input                 The input you want to test.
     * @return                      The {@link Message} your {@link EventListener} would return given the input. Test
     *                              this if it is correct.
     * @throws InterruptedException This code uses a {@link java.util.concurrent.CountDownLatch} that can the
     *                              interrupted if the thread is interrupted.
     */
    public static Message testGuildMessageReceivedEvent(EventListener listener, String input) throws InterruptedException {
        FakeGuildMessageReceivedEvent event = JDAObjects.getFakeGuildMessageReceivedEvent(input);
        listener.onEvent(event);
        return event.awaitReturn();
    }

    /**
     * Test a {@link EventListener} with a {@link FakeGuildMessageReceivedEvent}. The return will be tested against the
     * expectedOutput specified.
     *
     * @param listener              The {@link EventListener} that will be tested.
     * @param input                 The input you want to test.
     * @param expectedOutput        The output expected from the event.
     */
    public static void assertGuildMessageReceivedEvent(EventListener listener, String input, String expectedOutput) {
        FakeGuildMessageReceivedEvent event = JDAObjects.getFakeGuildMessageReceivedEvent(input);
        listener.onEvent(event);
        try {
            Assertions.assertEquals(expectedOutput, event.awaitReturn().getContentRaw());
        } catch (InterruptedException e) {
            Assertions.fail(e);
        }
    }

}
