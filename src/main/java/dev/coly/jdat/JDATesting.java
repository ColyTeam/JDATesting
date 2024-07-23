package dev.coly.jdat;

import dev.coly.util.Annotations;
import dev.coly.util.Callback;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import net.dv8tion.jda.api.interactions.commands.CommandInteraction;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

/**
 * This class should be used in testing. It creates fake {@link net.dv8tion.jda.api.events.Event}s and other JDA objects
 * to test your code.
 */
public class JDATesting {

    /**
     * Test a {@link EventListener} with a {@link MessageReceivedEvent}.
     * One can test the return to see if one's code returns the correct {@link Message} output for a given input.
     *
     * @param listener              The {@link EventListener} that will be tested.
     * @param input                 The input that will be used as the message.
     * @return                      The {@link Message} the {@link EventListener} would return given the input.
     *                              Test this if it is correct.
     * @throws InterruptedException This code uses a {@link CountDownLatch} that can the
     *                              interrupted if the thread is interrupted.
     */
    public static Message testMessageReceivedEvent(@NotNull EventListener listener, String input) throws InterruptedException {
        return testMessageReceivedEvent(input, listener::onEvent);
    }

    /**
     * Test a {@link Class} with methods annotated with {@link SubscribeEvent} using a {@link MessageReceivedEvent}.
     * One can test the return to see if one's code returns the correct {@link Message} outputs for a given input.
     * This method will test all methods annotated with {@link SubscribeEvent} in the provided {@link Class} and will
     * return only when all methods have been tested.
     * The order of the {@link Message} objects in the returned is the same order as the methods in the {@link Class}.
     *
     * @param clazz                 The {@link Class} that contains methods annotated with {@link SubscribeEvent} and
     *                              will be tested.
     * @param input                 The input that will be used as the message.
     * @return                      List of {@link Message}s the methods would return given the input.
     *                              Test if the messages are correct.
     * @throws InterruptedException This code uses a {@link CountDownLatch} that can be
     *                              interrupted if the thread is interrupted.
     */
    @NotNull
    public static List<Message> testMessageReceivedEvent(Class<?> clazz, String input) throws InterruptedException {
        List<Method> methods = Annotations.getMethodsAnnotatedWith(clazz, SubscribeEvent.class);
        List <Message> messages = new ArrayList<>();
        for (Method method : methods) {
            messages.add(testMessageReceivedEvent(input, (event) -> {
                try {
                    method.invoke(clazz.getDeclaredConstructor().newInstance(), event);
                } catch (Exception e) {
                    Assertions.fail("Could not invoke method with @SubscribeEvent annotation", e);
                }
            }));
        }
        return messages;
    }

    private static Message testMessageReceivedEvent(String input, @NotNull Consumer<MessageReceivedEvent> onEvent)
            throws InterruptedException {
        Callback<Message> messageCallback = Callback.single();

        MessageChannel channel = JDAObjects.getMessageChannel("test-chanel", 0L, messageCallback);
        Message message = JDAObjects.getMessage(input, new ArrayList<>(), channel);
        MessageReceivedEvent event = JDAObjects.getMessageReceivedEvent(channel, message,
                JDAObjects.getMember("Test User", "0000"));

        onEvent.accept(event);
        return messageCallback.await();
    }

    /**
     * Test a {@link EventListener} with a {@link MessageReceivedEvent}. The return will be tested against the
     * expectedOutput specified.
     *
     * @param listener              The {@link EventListener} that will be tested.
     * @param input                 The input you want to test.
     * @param expectedOutput        The output expected from the event.
     */
    public static void assertGuildMessageReceivedEvent(EventListener listener, String input, String expectedOutput) {
        try {
            Assertions.assertEquals(expectedOutput, testMessageReceivedEvent(listener, input).getContentRaw());
        } catch (InterruptedException e) {
            Assertions.fail(e);
        }
    }

    /**
     * Test a {@link EventListener} with a {@link MessageReceivedEvent}. The return will be tested against the
     * expectedOutput specified.
     *
     * @param listener              The {@link EventListener} that will be tested.
     * @param input                 The input you want to test.
     * @param expectedOutputs       A list of {@link MessageEmbed} to test against. The order is important.
     */
    public static void assertGuildMessageReceivedEvent(EventListener listener, String input,
                                                       List<MessageEmbed> expectedOutputs) {
        try {
            assertEmbeds(testMessageReceivedEvent(listener, input), expectedOutputs);
        } catch (InterruptedException e) {
            Assertions.fail(e);
        }
    }

    /**
     * Test a {@link EventListener} with a {@link SlashCommandInteractionEvent}.
     * One can test the return to see if one's code returns the correct {@link Message} output for a given input.
     *
     * @param listener              The {@link EventListener} that will be tested.
     * @param name                  The name of the command.
     * @param options               Options for this application command.
     * @return                      The {@link Message} the {@link EventListener} would return given the input.
     *                              Test this if it is correct.
     * @throws InterruptedException This code uses a {@link CountDownLatch} that can the interrupted if the thread
     *                              is interrupted.
     */
    public static Message testSlashCommandEvent(EventListener listener, String name, String subcommand,
                                                String subcommandGroup, Map<String, Object> options)
            throws InterruptedException {
        return testGenericCommandInteractionEvent(listener, name, subcommand, subcommandGroup, options,
                SlashCommandInteractionEvent.class);
    }

    /**
     * Test a {@link EventListener} with a {@link SlashCommandInteractionEvent}. The raw content of the return message will
     * be tested against the expectedOutput specified.
     *
     * @param listener          The {@link EventListener} that will be tested.
     * @param command           The command that will be tested.
     * @param options           Options that will be tested.
     * @param expectedOutput    The excepted string that {@link Message#getContentRaw()}} will have.
     */
    public static void assertSlashCommandEvent(EventListener listener, String command, Map<String, Object> options,
                                               String expectedOutput) {
        assertSlashCommandEvent(listener, command, null, null, options, expectedOutput);
    }

    /**
     * Test a {@link EventListener} with a {@link SlashCommandInteractionEvent}. The raw content of the return message will
     * be tested against the expectedOutput specified.
     *
     * @param listener          The {@link EventListener} that will be tested.
     * @param command           The command that will be tested.
     * @param subcommand        The name of the subcommand.
     * @param subcommandGroup   The name of the subcommand group.
     * @param options           Options that will be tested.
     * @param expectedOutput    The excepted string that {@link Message#getContentRaw()}} will have.
     */
    public static void assertSlashCommandEvent(EventListener listener, String command, String subcommand,
                                               String subcommandGroup, Map<String, Object> options,
                                               String expectedOutput) {
        assertGenericCommandInteractionEvent(listener, command, subcommand, subcommandGroup, options,
                SlashCommandInteractionEvent.class, expectedOutput);
    }

    /**
     * Test a {@link EventListener} with a {@link SlashCommandInteractionEvent}. The return will be tested against the
     * expectedOutput specified.
     *
     * @param listener          The {@link EventListener} that will be tested.
     * @param command           The command that will be tested.
     * @param options           Options that will be tested.
     * @param expectedOutputs   A list of {@link MessageEmbed} to test against. The order is important.
     */
    public static void assertSlashCommandEvent(EventListener listener, String command, Map<String, Object> options,
                                               List<MessageEmbed> expectedOutputs) {
        assertSlashCommandEvent(listener, command, null, null, options, expectedOutputs);
    }

    /**
     * Test a {@link EventListener} with a {@link SlashCommandInteractionEvent}. The return will be tested against the
     * expectedOutput specified.
     *
     * @param listener          The {@link EventListener} that will be tested.
     * @param command           The command that will be tested.
     * @param subcommand        The name of the subcommand.
     * @param subcommandGroup   The name of the subcommand group.
     * @param options           Options that will be tested.
     * @param expectedOutputs   A list of {@link MessageEmbed} to test against. The order is important.
     */
    public static void assertSlashCommandEvent(EventListener listener, String command, String subcommand,
                                               String subcommandGroup, Map<String, Object> options,
                                               List<MessageEmbed> expectedOutputs) {
        assertGenericCommandInteractionEvent(listener, command, subcommand, subcommandGroup, options,
                SlashCommandInteractionEvent.class, expectedOutputs);
    }

    /**
     * Test a {@link EventListener} with a {@link MessageContextInteractionEvent}.
     * One can test the return to see if one's code returns the correct {@link Message} output for a given input.
     *
     * @param listener              The {@link EventListener} that will be tested.
     * @param name                  The name of the command.
     * @param options               Options for this application command.
     * @return                      The {@link Message} the {@link EventListener} would return given the input.
     *                              Test this if it is correct.
     * @throws InterruptedException This code uses a {@link CountDownLatch} that can the interrupted if the thread
     *                              is interrupted.
     */
    public static Message testMessageContextInteractionEvent(EventListener listener, String name,
                                                             Map<String, Object> options) throws InterruptedException {
        return testGenericCommandInteractionEvent(listener, name, null, null, options,
                MessageContextInteractionEvent.class);
    }

    private static <T extends GenericCommandInteractionEvent> Message
        testGenericCommandInteractionEvent(EventListener listener, String name, String subcommand, String subgroup,
                                           Map<String, Object> options, Class<T> clazz)
            throws InterruptedException {
        Callback<Message> messageCallback = Callback.single();

        MessageChannel channel = JDAObjects.getMessageChannel("test-chanel", 0L, messageCallback);
        Member member = JDAObjects.getMember("member", "0000");
        CommandInteraction interaction = JDAObjects.getCommandInteraction(channel, member, name, subcommand, subgroup,
                options, CommandInteraction.class);
        T event = JDAObjects.getCommandInteractionEvent(interaction, messageCallback, Callback.single(), clazz);

        listener.onEvent(event);
        return messageCallback.await();
    }

    private static <T extends GenericCommandInteractionEvent> void
    assertGenericCommandInteractionEvent(EventListener listener, String command, String subcommand, String subgroup,
                                         Map<String, Object> options, Class<T> type, String expectedOutput) {
        try {
            Assertions.assertEquals(
                    testGenericCommandInteractionEvent(listener, command, subcommand, subgroup, options, type)
                            .getContentRaw(),
                    expectedOutput);
        } catch (InterruptedException e){
            Assertions.fail(e);
        }
    }

    private static <T extends GenericCommandInteractionEvent> void
    assertGenericCommandInteractionEvent(EventListener listener, String command, String subcommand, String subgroup,
                                         Map<String, Object> options, Class<T> type,
                                         List<MessageEmbed> expectedOutputs) {
        try {
            assertEmbeds(testGenericCommandInteractionEvent(listener, command, subcommand, subgroup, options, type),
                    expectedOutputs);
        } catch (InterruptedException e) {
            Assertions.fail(e);
        }
    }

    /**
     * Test a {@link EventListener} with a {@link MessageContextInteractionEvent}. The return will be tested against the
     * expectedOutput specified.
     *
     * @param listener          The {@link EventListener} that will be tested.
     * @param command           The command that will be tested.
     * @param options           Options that will be tested.
     * @param expectedOutputs   A list of {@link MessageEmbed} to test against. The order is important.
     */
    public static void assertMessageContextInteractionEvent(EventListener listener, String command,
                                                            Map<String, Object> options, List<MessageEmbed> expectedOutputs) {
        assertGenericCommandInteractionEvent(listener, command, null, null, options,
                MessageContextInteractionEvent.class, expectedOutputs);
    }


    /**
     * Test a {@link EventListener} with a {@link MessageContextInteractionEvent}. The raw content of the return message
     * will be tested against the expectedOutput specified.
     *
     * @param listener          The {@link EventListener} that will be tested.
     * @param command           The command that will be tested.
     * @param options           Options that will be tested.
     * @param expectedOutput    The excepted string that {@link Message#getContentRaw()}} will have.
     */
    public static void assertMessageContextInteractionEvent(EventListener listener, String command,
                                                            Map<String, Object> options, String expectedOutput) {
        assertGenericCommandInteractionEvent(listener, command, null, null, options,
                MessageContextInteractionEvent.class, expectedOutput);
    }

    private static void assertEmbeds(Message message, List<MessageEmbed> expectedOutputs) {
        Assertions.assertEquals(expectedOutputs.size(), message.getEmbeds().size(), "Number of embeds");

        int i = 0;
        for (MessageEmbed embed : message.getEmbeds()) {
            MessageEmbed expectedOutput = expectedOutputs.get(i);
            assertEmbed(expectedOutput, embed);
            i++;
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
