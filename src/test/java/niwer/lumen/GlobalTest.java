package niwer.lumen;

import java.io.File;

import niwer.lumen.container.ConsoleFileManager;
import niwer.lumen.types.BasicLogType;
import niwer.lumen.types.DefaultLogTypes;
import niwer.lumen.types.ILogType;

/**
 * Lumen use a container based system.
 * This means that you can create your own container and use it to log messages to different places without changing the code that logs the messages.
 * For example you can create a container for your game and if you have a rendering part which absolutely NEEDS to be seperate from the rest of the code, you can create a container for it and log messages to it without changing the code that logs the messages.
 */
public class GlobalTest {

    public static void main(String[] args) {
        /*
            First of all, You can call this function to ensure avoiding conflicts with other logging systems, such as java.util.logging, which may interfere with Lumen's logging if not properly configured.
            By default, if you don't call this function the default print system will also receiv de message and print it. So you may see duplicate messages in the console.
        */
        LumenEngine.removeDefaultHandlers();
        // LumenEngine.disablePrintingFromDefaultContainer();
        LumenEngine.DEFAULT_CONTAINER.enablePrinting(); // Force enabling the printing (Should not be required. Buf If for some reason you want to disable printing and then re-enable it. You can)

        /* This will use the default container */
        {
            /* Send basic message */
            Console.log("Hello %s", "world").send();
            
            /* Send object directly */
            Console.log(25).send();

            /* Send exception */
            Console.log(new Exception("Test exception")).send();
            
            /* Send exception with "prefix" message */
            Console.log("This is a prefix for the error.", new Exception("Test exception")).send();
        }

        /* Types */
        {
            /*
                Types are just a way to categorize your messages. For exemple you may want to categorize all messaged printed by your Client side code as "client" or all messages printed by your Server side code as "server", and so on.

                To create your own types, we recommend you to use the "BasicLogType" class, which is a simple implementation of the "ILogType" interface
            */
            final ILogType SQL = new BasicLogType("SQL", EnumLogColor.BLACK);
            Console.log("This is a message with the SQL type").type(SQL).send();

            final ILogType NETWORK = new BasicLogType("NETWORK", EnumLogColor.YELLOW);
            Console.log("This is a message with the NETWORK type").type(NETWORK).send();
           
            /*
                There are also a few default types. See DefaultLogTypes and the default selected type for all logs is DefaultLogTypes.NONE, which won't show any type.
            */
            Console.log("This is a message with the NETWORK type").type(DefaultLogTypes.INFO).send();
        }

        /* Save to file */
        {
            /* Creating a container */
            final var MY_CONTAINER = LumenEngine.registerContainer("MyContainer").showNameInLogs(); // "showNameInLogs" will add [MyContainer] to the messages.

            /* Once the container is registered, you simply call the "container" function of Console. */
            Console.log("This message will be logged to MyContainer").container(MY_CONTAINER).send();

            /*
                The advantage of the container is that you can print the logged message inside a file.
                Note that the default container won't print to a file by default.

                You can register a file handler to the container, which will print all messages logged to that container to a file.
                Note that registering a file handler, will also add a shutdown hook to save the file after closing the application, so you don't have to worry about that.
            */
           final File FOLDER = new File("./logs/");
           ConsoleFileManager.registerFileForDefaultContainer(FOLDER, "the_base_name_of_the_file"); // This will register a file handler to the default container, which will print all messages logged to the default container to a file named "the_base_name_of_the_file-DATE.log"
        
            /* And of course you can register a file handler */
            ConsoleFileManager.registerFileFor(FOLDER, MY_CONTAINER, "the_base_name_of_the_file_for_my_container"); // This will register a file handler to MY_CONTAINER, which will print all messages logged to MY_CONTAINER to a file named "the_base_name_of_the_file_for_my_container-DATE.log"
        
            Console.log("This message will be in the .log file").container(MY_CONTAINER).error().send();
        }
        
        /* Discord / Stoat / Custom */
        {
            /*
                I wanted a way to log messages to custom places, such as a Discord channel or a Stoat channel, without having to change the code that logs the messages.

                So to handle your own custom stuff, you have to register processors.

                Note that in order to make your log sent to processor you have to use "Console.sendToProcessor()".
            */
           LumenEngine.registerExternalProcessorForDefaultContainer((data, time, formattedMessage) -> {
                Console.log("Received message in processor: " + formattedMessage).send(); // This is just to show that the message is received in the processor, you can remove this line and implement your own logic.
                Console.log("%s ", data.isError()).container(data.container()).send(); // This is just to show that you can access the container of the message, you can remove this line and implement your own logic.

                /*
                    Implement your custom processing logic here.

                    // This is a pseudo code for example.
                    JDABot.getChannelById("channel_id").sendMessage(formattedMessage).addFile(file).queue();

                    JDABot.getChannelById("channel_id").createEmbed(embed -> {
                        embed.setTitle("New log message");
                        embed.setDescription(formattedMessage);
                        embed.addField("File", file.getName(), false);
                    }).addFile(file).queue();
                */
            });
            Console.log("This message will be in the .log file").sendToProcessor().error().send();

            /* For custom container */
            final var MY_CONTAINER = LumenEngine.registerContainer("MyContainer");
            LumenEngine.registerExternalProcessor(MY_CONTAINER, (data, time, formattedMessage) -> {
                // Do your custom processing logic here for MY_CONTAINER
            });

            /*
                Note you can also chain the addProcessor function directly on the container.
                final var MY_CONTAINER = LumenEngine.registerContainer("MyContainer").addProcessor((data, time, formattedMessage) -> {});
            */

            Console.log("This message will be in the .log file").container(MY_CONTAINER).sendToProcessor().error().send();
        }
    }
}
