package niwer.lumen.files;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * This is a container for a console that hold the Handler and Logger of it.
 * 
 * @author Niwer
 */
public class Container {

    private String NAME;
    private final ConsoleHandler HANDLER;
    private final Logger LOGGER;

    protected Container(String name) {
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
     * Add a FileHandler to the container's logger.
     * @param fileHandler The FileHandler to add
     */
    public void addFileHandler(FileHandler fileHandler) { this.LOGGER.addHandler(fileHandler); }

    public ConsoleHandler handler() { return HANDLER; }

    public Logger logger() { return this.LOGGER; }

    public String name() { return this.NAME; }
}
