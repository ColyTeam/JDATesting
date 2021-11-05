# JDATesting
Test framework for the [JDA Discord api wrapper](https://github.com/DV8FromTheWorld/JDA).

## Installation
Download the jar file from the [release page](https://github.com/ColyTeam/JDATesting/releases/latest).

### Maven

```xml
<repository>
    <id>coly</id>
    <url>https://m2.coly.dev/releases</url>
</repository>

<dependency>
    <groupId>dev.coly</groupId>
    <artifactId>JDATesting</artifactId>
    <version>VERSION</version>
</dependency>
````

## Usage
This project should be used to your JDA Discord bot using maven tests.

### Examples
This examples use junit.

For default commands using GuildMessageReceivedEvent:
```JAVA
@Test
public void testAssertGuildMessageReceivedEvent() {
    JDATesting.assertGuildMessageReceivedEvent(new TestEventListener(), ".ping", "Pong!");
}

public static class TestEventListener implements EventListener {

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (event instanceof GuildMessageReceivedEvent) {
            GuildMessageReceivedEvent e = (GuildMessageReceivedEvent) event;
            if (e.getMessage().getContentDisplay().equals(".ping")) {
                e.getChannel().sendMessage("Pong!").queue();
            }
        }
    }

}
```

```JAVA
@Test
public void testTestGuildMessageReceivedEvent() {
    try {
        Assertions.assertEquals("Pong!",
                JDATesting.testGuildMessageReceivedEvent(new TestEventListener(), ".ping").getContentRaw());
    } catch (InterruptedException e) {
        Assertions.fail(e);
    }
}

public static class TestEventListener implements EventListener {

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (event instanceof GuildMessageReceivedEvent) {
            GuildMessageReceivedEvent e = (GuildMessageReceivedEvent) event;
            if (e.getMessage().getContentDisplay().equals(".ping")) {
                e.getChannel().sendMessage("Pong!").queue();
            }
        }
    }

}
```

For Slash Commands:
```JAVA
@Test
public void testAssertSlashCommandEvent() {
    JDATesting.assertSlashCommandEvent(new TestEventListener(), "ping", new HashMap<>(), "Pong!");
}

@Test
public void testAssertSlashCommandEventWithOptions() {
    Map<String, Object> map = new HashMap<>();
    map.put("bool", true);
    map.put("str", "text");
    map.put("number", 42);
    map.put("user", JDAObjects.getFakeUser());
    JDATesting.assertSlashCommandEvent(new TestEventListener(), "options", map, 
        "bool: true - str: text - number: 42 - user: User#0000");
}

@Test
public void testAssertSlashCommandEventWithEmbeds() {
    JDATesting.assertSlashCommandEvent(new TestEventListener(), "embed", new HashMap<>(), 
        new ArrayList<>(Collections.singleton(TestEventListener.getTestEmbed())));
}

public class TestEventListener implements EventListener {

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (event instanceof SlashCommandEvent) {
            SlashCommandEvent e = (SlashCommandEvent) event;
            switch (e.getName()) {
                case "embed":
                    e.replyEmbeds(getTestEmbed()).queue();
                    break;
                case "ping":
                    e.reply("Pong!").queue();
                    break;
                case "options":
                    e.reply("bool: " + Objects.requireNonNull(e.getOption("bool")).getAsBoolean() +
                            " - str: " + Objects.requireNonNull(e.getOption("str")).getAsString() +
                            " - number: " + Objects.requireNonNull(e.getOption("number")).getAsLong() +
                            " - user: " + Objects.requireNonNull(e.getOption("user")).getAsUser().getAsTag()).queue();
            }
        }
    }

    public static MessageEmbed getTestEmbed() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Test Embed");
        embedBuilder.setAuthor("Coly Team");
        embedBuilder.addField("Test Name", "Test Value", true);
        return embedBuilder.build();
    }

}
```