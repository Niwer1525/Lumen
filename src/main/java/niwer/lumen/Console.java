package niwer.lumen;

import java.io.File;
import java.util.logging.Level;

import niwer.lumen.container.Container;
import niwer.lumen.container.Processor;
import niwer.lumen.types.DefaultLogTypes;
import niwer.lumen.types.ILogType;

public class Console {
    private Container container = LumenEngine.DEFAULT_CONTAINER;
    private Object message;
    private ILogType type = DefaultLogTypes.NONE;
    private boolean isError = false;
    private boolean sendToProcessors = false;
    private File file = null;

    public String formattedMessage() {
        return this.formatMessage(System.currentTimeMillis());
    }

    public Object message() { return this.message; }

    public ILogType type() { return this.type; }

    public boolean isError() { return this.isError; }

    public Container container() { return this.container; }

    public File file() { return this.file; }

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
        final Console CONSOLE = new Console();
        CONSOLE.message = message;
        return CONSOLE;
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
	
    public Console type(ILogType type) {
        if(type == null) throw new IllegalArgumentException("Log type cannot be null");

        this.type = type;
        return this;
    }

    public Console sendToProcessor() {
        this.sendToProcessors = true;
        return this;
    }

    /**
     * This will attach a file to the log, which can be used by external processors (e.g. a Discord processor that sends the log message and the file to a specific channel).
     */
    public Console file(File file) {
        this.file = file;
        return this;
    }

    /**
     * Set the container to use for this log. If the container is null, it will use the default container.
     * 
     * @param container The container to use for this log
     * @return The Console instance for chaining
     * 
     * @see LumenEngine.DEFAULT_CONTAINER
     * @see Container
     */
    public Console container(Container container) {
        this.container = container == null ? LumenEngine.DEFAULT_CONTAINER : container;
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
        if(!this.container.isPrintingEnabled()) throw new IllegalStateException("Cannot send log to console because printing is disabled for the container: " + this.container.name());
        if(message == null) return; // If no message, do nothing

        /* Format the message */
        final long TIME = System.currentTimeMillis();
        final String FORMATTED_MESSAGE = this.formatMessage(TIME);

        /* Log to the terminal */
        this.container.logger().log(Level.INFO, FORMATTED_MESSAGE);

        /* Send to processors */
        if(this.sendToProcessors) {
            for (final Processor PROCESSOR : this.container.processors()) PROCESSOR.process(this, TIME, FORMATTED_MESSAGE);
        }
    }

    private String formatMessage(long time) {
        final String TIMESTAMP = String.format("[%tT] ", time);
        final String CONTAINER_PREFIX = this.container.shouldShowNameInLogs() ? EnumLogColor.BLACK + "[" + this.container.name() + "] " + EnumLogColor.RESET : "";
        final String ERROR_PREFIX = isError ? EnumLogColor.RED + "[ERROR] " + EnumLogColor.RESET : "";
        final String PREFIX = type == DefaultLogTypes.NONE ? "" : type.color() + "[" + type.name() + "] " + EnumLogColor.RESET;
        return String.format("%s%s%s%s%s%s", CONTAINER_PREFIX, ConsoleUtils.getLineCaller(), TIMESTAMP, ERROR_PREFIX, PREFIX, message);

    }
}
