package niwer.lumen.container;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import niwer.lumen.LumenEngine;

/**
 * This is a container for a console that hold the Handler and Logger of it.
 * 
 * @author Niwer
 */
public class Container {

    private final Set<Processor> PROCESSORS = new HashSet<>();
    private final String NAME;
    private final ConsoleHandler HANDLER;
    private final Logger LOGGER;

    private boolean isPrintingEnabled = true;
    private boolean showNameInLogs = false;

    public Container(String name) {
        this.NAME = name;
        this.HANDLER = new ConsoleHandler();
        this.LOGGER = Logger.getLogger(name);

        /* Encode to UTF-8 */
		try {
			this.HANDLER.setEncoding("UTF-8");
            // this.HANDLER.setUseParentHandlers(false);
            this.HANDLER.setFormatter(new Formatter(false));
			this.LOGGER.addHandler(HANDLER);
		} catch (IOException e) {
			throw new RuntimeException("Failed to set console handler encoding to UTF-8", e);
		}
    }

    /**
     * Disable printing to the console for this container.
     * This means that Console.log().send() will thrown an exception.
     * @return This container for chaining
     */
    public Container disablePrinting() {
        this.isPrintingEnabled = false;
        return this;
    }

    /**
     * Enable printing to the console for this container.
     * @return This container for chaining
     */
    public Container enablePrinting() {
        this.isPrintingEnabled = true;
        return this;
    }

    /**
     * Add a processor to the container, which will be called when a log is sent to this container.
     * This can be used to modify the log message or perform additional actions (e.g. send the log message to a Discord channel).
     * 
     * @see LumenEngine#registerExternalProcessor
     * @return This container for chaining
     */
    public Container addProcessor(Processor processor) {
        this.PROCESSORS.add(processor);
        return this;
    }

    /**
     * Add a FileHandler to the container's logger.
     * @param fileHandler The FileHandler to add
     * 
     * @see ConsoleFileManager#registerFileFor(java.io.File, Container, String)
     * @return This container for chaining
     */
    public Container addFileHandler(FileHandler fileHandler) {
        this.LOGGER.addHandler(fileHandler);
        return this;
    }

    /**
     * Show the container name in the log messages for this container.
     * This will add a prefix with the container name to the log messages ([CONTAINER_NAME]).
     * 
     * @return This container for chaining
     */
    public Container showNameInLogs() {
        this.showNameInLogs = true;
        return this;
    }

    public ConsoleHandler handler() { return HANDLER; }

    public Logger logger() { return this.LOGGER; }

    public String name() { return this.NAME; }

    public Set<Processor> processors() { return new HashSet<>(this.PROCESSORS); } // Create a new HashSet to prevent external modification of the original set

    public boolean isPrintingEnabled() { return this.isPrintingEnabled; }

    public boolean shouldShowNameInLogs() { return this.showNameInLogs; }
}