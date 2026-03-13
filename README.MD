# Lumen a pure Java lightweight logger.
Lumen is a pure Java lightweight logger. It is designed to be simple to use and to be easily extensible. Lumen is not a logging framework, it is a logging system. It provides a way to log messages with the most basics configurations. You can use Lumen to log messages to the console, to a file, or to any other system you want by creating your own processors (Discord, Stoat, etc).

## Features
- Container - A container is a logger. (For example you can have a container for messages printed by your server application which will send everything on discord and save to .log files)
- Console - The main system to print log messages to the console.
- File saving - Lumen has a way to save logged message to a file.
- Processor - A system to process log messages in order to send them to other systems (For example you can use a processor to send logs to Discord).
- Types - A customizable category system to categorize your messages.
- Colors - Lumen has a lot of colors

# How to install
![GitHub release (latest by date)](https://img.shields.io/github/v/release/Niwer1525/Lumen?label=latest%20version&color=orange)
Or [📥Download the jar](https://github.com/Niwer1525/Lumen/releases/latest/download/lumen-logger.jar)

View on JitPack : https://jitpack.io/#Niwer1525/Lumen/v1.0.1

Gradle:
```groovy
repositories {
    mavenCentral();
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.Niwer1525:Lumen:1.0.0'
}
```
Maven:
```xml
<repositories>
    <repository>
        <id>maven-central</id>
        <url>https://repo.maven.apache.org/maven2/</url>
    </repository>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
<dependencies>
    <dependency>
        <groupId>com.github.Niwer1525</groupId>
        <artifactId>Lumen</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

# How to use
First of all, if you want a full Java break down, go in the `src/test/java/GlobalTest` Class.

First to ensure Lumen works properly, you need to disable all other logging system.
```java
ContainerManager.removeDefaultHandlers();
```

Basic logging is really simple, you just need to do:
```java
Console.log("Hello World!").send();
```
You can do more since Console is a builder (You can chain functions with send being the last):
```java
Console
    .log("Hello World!")
    .error() // Will mark the message as an error. (Adds [ERROR] to the message)
    .type(DefaultLogTypes.INFO) // Types are a way to categorize your messages. (Adds [INFO] to the message)
    .sendToProcessor() // Allows to send to other systems using processors, see below for more details
    .file(new File("file")) // Allows to attach file to message. You have to create processors to handle this. (No default handling)
    .container(ContainerManager.DEFAULT_CONTAINER) // Or your custom container, see below for more details
    .send();
```
The `log` function is declined in multiple functions to allow you to use formatting:
```java
Console.log("Hello %s - %d", "world", 25).send(); // Will be formatted as 'Hello World - 25'

Console.log(25).send(); // You can send any object.

Console.log(new Exception("Test exception")).send(); // This will be automatically marked as an error and will print the stacktrace

Console.log("This is a prefix for the error.", new Exception("Test exception")).send(); // This will be automatically marked as an error and will print the stacktrace
```

You are not forced to create a container. Lumen has, of course, a default container that you can register file saver or processors to. But if you want to create your own container, you can do it like this:
```java
final var MY_CONTAINER = ContainerManager.registerContainer("MyContainer");
```

The default container is called `DEFAULT_CONTAINER` and you can get it like this:
```java
ContainerManager.DEFAULT_CONTAINER
```

To save your printed message for your container you can do :
```java
final var MY_CONTAINER = ContainerManager.registerContainer("MyContainer");
final File FOLDER = new File("./logs/");
ConsoleFileManager.registerFileFor(FOLDER, MY_CONTAINER, "the_base_name_of_the_file_for_my_container");
```
If you want to use the default container, you can do:
```java
final File FOLDER = new File("./logs/");
ConsoleFileManager.registerFileForDefaultContainer(FOLDER, "the_base_name_of_the_file");
```

Processors are a way to process logged messages to send them to other systems. For example, you can use a processor to send logs to Discord. You can register a processor for a container like this:
```java
final var MY_CONTAINER = ContainerManager.registerContainer("MyContainer");
ContainerManager.registerExternalProcessor(MY_CONTAINER, (data, time, formattedMessage) -> {
    // Do your custom processing logic here for MY_CONTAINER
});
```

Types are a way to categorize your messages. You can use the `BasicLogType` class which is a simple implementation of the `ILogType` interface. You can also create your own types by implementing the `ILogType` interface. To set the type of a message, you can do:
```java
final ILogType SQL = new BasicLogType("SQL", EnumLogColor.BLACK);
Console.log("This is a message with the SQL type").type(SQL).send();

final ILogType NETWORK = new BasicLogType("NETWORK", EnumLogColor.YELLOW);
Console.log("This is a message with the NETWORK type").type(NETWORK).send();
```
There are few default types in `DefaultLogTypes` class. The default selected type for all logs is `DefaultLogTypes.NONE`, which won't show any type.
```java
Console.log("This is a message with the NETWORK type").type(DefaultLogTypes.INFO).send();
```
