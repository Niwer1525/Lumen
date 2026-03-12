package niwer.lumen;

import java.io.File;
import java.util.logging.Level;

import niwer.lumen.files.Container;
import niwer.lumen.files.ContainerManager;

public class Console {
    private Container container = ContainerManager.DEFAULT_CONTAINER;
    private Object message;
    private EnumLogType type = EnumLogType.NONE;
    private boolean isError = false;

    public Object getMessage() { return this.message; }

    /**
     * Log a message to the console.
     * E.G : <code>Console.log("Hello %s", "world")</code> will log "Hello world"
     * 
     * @param message The message to log, which can be a String with format specifiers (e.g. "%s") for the optional arguments.
     * @param args Optional arguments to format the message (if the message is a String with format specifiers).
     * @return The Console instance for chaining
     */
    public static Console log(String message, Object... args) {
        final Console CONSOLE = new Console();
        CONSOLE.message = String.format(message, args);
        return CONSOLE;
    }

    /**
     * Log a message to the console.
     * 
     * @param message The message to log
     * @return The Console instance for chaining
     */
    public static Console log(Object message) {
        return log("%s", message);
    }

    /**
     * Log a Throwable's stack trace as an error.
     * This will convert the stack trace to a String and log as an <code>error</code>.
     * 
     * @param message The message to log before the stack trace
     * @param t The Throwable to log
     * @return The Console instance for chaining
     */
    public static Console log(String message, Throwable t) {
        return log(message + "\n%s", ConsoleUtils.stringify(t)).error();
    }

    /**
     * Log a Throwable's stack trace as an error.
     * This will convert the stack trace to a String and log as an <code>error</code>.
     * @param t The Throwable to log
     * @return The Console instance for chaining
     */
    public static Console log(Throwable t) {
        return log(ConsoleUtils.stringify(t)).error();
	}
	
    public Console type(EnumLogType type) {
        if(type == null) throw new IllegalArgumentException("Log type cannot be null");

        this.type = type;
        return this;
    }

    public Console showOnDiscord() {
        throw new UnsupportedOperationException("Discord logging is not supported yet.");
    }

    public Console file(File file) {
        throw new UnsupportedOperationException("File logging is not supported yet.");
    }

    /**
     * Set the container to use for this log. If the container is null, it will use the default container.
     * 
     * @param container The container to use for this log
     * @return The Console instance for chaining
     * 
     * @see ContainerManager.DEFAULT_CONTAINER
     * @see Container
     */
    public Console container(Container container) {
        this.container = container == null ? ContainerManager.DEFAULT_CONTAINER : container;
        return this;
    }

    /**
     * Mark the log as an error, which will add an "[ERROR]" prefix to the log and change its color to red.
     * 
     * @return The Console instance for chaining
     */
    public Console error() {
        this.isError = true;
        return this;
    }
    
    /**
     * Send the log to the console.
     */
    public void send() {
        if(message == null) return; // If no message, do nothing

        final String ERROR_PREFIX = isError ? EnumLogColor.RED + "[ERROR] " + EnumLogColor.RESET : "";
        final String PREFIX = type == EnumLogType.NONE ? "" : type.color + "[" + type.name() + "] " + EnumLogColor.RESET;
        final String TIMESTAMP = String.format("[%tT] ", System.currentTimeMillis());
        final String MESSAGE = String.format("%s%s%s%s%s", ConsoleUtils.getLineCaller(), TIMESTAMP, ERROR_PREFIX, PREFIX, message);

        this.container.logger().log(Level.INFO, MESSAGE);
    }
}
