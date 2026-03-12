package niwer.lumen;

import java.io.File;

import niwer.lumen.files.ConsoleFileManager;
import niwer.lumen.files.ContainerManager;

/**
 * Lumen use a container based system.
 * This means that you can create your own container and use it to log messages to different places without changing the code that logs the messages.
 * For example you can create a container for your game and if you have a rendering part which absolutely NEEDS to be seperate from the rest of the code, you can create a container for it and log messages to it without changing the code that logs the messages.
 */
public class GlobalTest {

    public static void main(String[] args) {
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
            */
           //TODO
        }

        /* Save to file */
        {
            ContainerManager.removeDefaultHandlers(); // You can call this function to ensure avoiding conflicts with other logging systems, such as java.util.logging, which may interfere with Lumen's logging if not properly configured.

            /* Creating a container */
            final var MY_CONTAINER = ContainerManager.registerContainer("MyContainer");

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
            */
           //TODO
        }
    }
}
