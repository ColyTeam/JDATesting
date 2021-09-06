package dev.coly.jdat;

import dev.coly.jdat.entities.actions.FakeReplyAction;
import dev.coly.jdat.entities.events.FakeGuildMessageReceivedEvent;
import dev.coly.jdat.entities.events.FakeSlashCommandEvent;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.hooks.EventListener;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    /**
     * Test a {@link EventListener} with a {@link FakeGuildMessageReceivedEvent}. The return will be tested against the
     * expectedOutput specified.
     *
     * @param listener              The {@link EventListener} that will be tested.
     * @param input                 The input you want to test.
     * @param expectedOutputs       A list of {@link MessageEmbed} to test against. The order is important.
     */
    public static void assertGuildMessageReceivedEvent(EventListener listener, String input, List<MessageEmbed> expectedOutputs) {
        FakeGuildMessageReceivedEvent event = JDAObjects.getFakeGuildMessageReceivedEvent(input);
        listener.onEvent(event);
        try {
            Message message = event.awaitReturn();
            int i = 0;
            for (MessageEmbed embed : message.getEmbeds()) {
                MessageEmbed expectedOutput = expectedOutputs.get(i);
                assertEmbed(expectedOutput, embed);
                i++;
            }
        } catch (InterruptedException e) {
            Assertions.fail(e);
        }
    }

    /**
     * Test a {@link EventListener} with a {@link FakeSlashCommandEvent}. The return will be tested against the
     * expectedOutput specified.
     *
     * @param listener          The {@link EventListener} that will be tested.
     * @param command           The command that will be tested.
     * @param options           Options that will be tested.
     * @param expectedOutputs   A list of {@link MessageEmbed} to test against. The order is important.
     */
    public static void assertSlashCommandEvent(EventListener listener, String command, Map<String, Object> options,
                                               List<MessageEmbed> expectedOutputs) {
        FakeSlashCommandEvent event = JDAObjects.getFakeSlashCommandEvent(command, 0, options);
        listener.onEvent(event);
        try {
            FakeReplyAction action = event.awaitReturn();
            int i = 0;
            for (MessageEmbed embed : action.getMessage().getEmbeds()) {
                MessageEmbed expectedOutput = expectedOutputs.get(i);
                assertEmbed(expectedOutput, embed);
                i++;
            }
        } catch (InterruptedException e) {
            Assertions.fail(e);
        }
    }

    /**
     * Test if the provided {@link MessageEmbed} is equal with the expcted {@link MessageEmbed}
     *
     * @param embed             The {@link MessageEmbed} provided to test.
     * @param expectedOutput    The expected {@link MessageEmbed}.
     */
    public static void assertEmbed(MessageEmbed embed, MessageEmbed expectedOutput) {
        Assertions.assertEquals(expectedOutput.getTitle(), embed.getTitle());
        Assertions.assertEquals(expectedOutput.getColor(), embed.getColor());
        Assertions.assertEquals(expectedOutput.getDescription(), embed.getDescription());
        Assertions.assertEquals(expectedOutput.getTimestamp(), embed.getTimestamp());
        Assertions.assertEquals(expectedOutput.getUrl(), embed.getUrl());
        if (expectedOutput.getAuthor() != null)
            Assertions.assertEquals(expectedOutput.getAuthor().getName(),
                    Objects.requireNonNull(embed.getAuthor()).getName());
        if (expectedOutput.getFooter() != null) {
            Assertions.assertEquals(expectedOutput.getFooter().getText(),
                    Objects.requireNonNull(embed.getFooter()).getText());
            Assertions.assertEquals(expectedOutput.getFooter().getIconUrl(),
                    Objects.requireNonNull(embed.getFooter()).getIconUrl());
        }
        if (expectedOutput.getImage() != null)
            Assertions.assertEquals(expectedOutput.getImage().getUrl(),
                    Objects.requireNonNull(embed.getImage()).getUrl());
        if (expectedOutput.getThumbnail() != null)
            Assertions.assertEquals(expectedOutput.getThumbnail().getUrl(),
                    Objects.requireNonNull(embed.getThumbnail()).getUrl());
        int i = 0;
        for (MessageEmbed.Field field : embed.getFields()) {
            Assertions.assertEquals(expectedOutput.getFields().get(i).getName(), field.getName());
            Assertions.assertEquals(expectedOutput.getFields().get(i).getValue(), field.getValue());
            Assertions.assertEquals(expectedOutput.getFields().get(i).isInline(), field.isInline());
            i++;
        }
    }

}
