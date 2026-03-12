package niwer.lumen.files;

import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import niwer.lumen.Console;

public class ContainerManager {

    public static final Container DEFAULT_CONTAINER = registerContainer("Lumen-Default");

    /**
     * Removes all default handlers from the root logger, which prevents duplicate logs when using custom handlers.
     */
    public static void removeDefaultHandlers() {
		final Logger ROOT_LOGGER = LogManager.getLogManager().getLogger("");
		final Handler[] HANDLERS = ROOT_LOGGER.getHandlers();
		for (final Handler HANDLER : HANDLERS) ROOT_LOGGER.removeHandler(HANDLER);
    }

	/**
	 * Register a new console logger/handler with custom name.
	 * This allows for multiple loggers with different handlers to be used in the application, which can be useful for logging to different files or consoles.
	 * 
	 * @param name The name of the logger/handler to register. Should be unique to avoid conflicts with other loggers/handlers.
	 * @return The registered console container.
	 */
	public static Container registerContainer(String name) {
		return new Container(name);
	}
	
	@Deprecated(since = "1.0.0")
	public static void setupThreadExceptionHandler() {
		Thread.setDefaultUncaughtExceptionHandler((t, e) -> Console.log("Uncaught exception in thread " + t.getName(), e).send());
	}
}
