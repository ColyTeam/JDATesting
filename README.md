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
