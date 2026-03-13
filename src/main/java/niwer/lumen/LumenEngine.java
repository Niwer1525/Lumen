package niwer.lumen;

import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import niwer.lumen.container.Container;
import niwer.lumen.container.Processor;

public class LumenEngine {

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
	 * Disable printing from the default container.
	 * This is useful if you don't want to use the default container.
	 * 
	 * @note If someone tries to print with the default container, this will cause Console.log().send() to throw an exception, so make sure to handle that if you disable printing from the default container.
	 */
	public static void disablePrintingFromDefaultContainer() {
		DEFAULT_CONTAINER.disablePrinting();
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
	
	/**
	 * Register an external application processor.
	 * For exemple you can register a processor for discord that will send the mesage in a specific channel with the file attached if there is one, and so on.
	 * 
	 * @param container The container to register the processor for
	 * @param processor The processor to register
	 * @return The updated container
	 */
	public static Container registerExternalProcessor(Container container, Processor processor) {
		container.addProcessor(processor);
		return container;
	}

	/**
	 * Register an external application processor for the default container.
	 * For exemple you can register a processor for discord that will send the mesage in a specific channel with the file attached if there is one, and so on.
	 * 
	 * @param processor The processor to register
	 * @return The updated default container
	 */
	public static Container registerExternalProcessorForDefaultContainer(Processor processor) {
		return registerExternalProcessor(LumenEngine.DEFAULT_CONTAINER, processor);
	}

	@Deprecated(since = "1.0.0")
	public static void setupThreadExceptionHandler() {
		Thread.setDefaultUncaughtExceptionHandler((t, e) -> Console.log("Uncaught exception in thread " + t.getName(), e).send());
	}
}
