package niwer.lumen.files;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;

import niwer.lumen.ConsoleUtils;

/**
 * @author Niwer
 */
public class ConsoleFileManager {

    /**
	 * Register a file handler to save logs (This will be the default handler)
	 * @param folder The folder to save logs
	 */
	public static void registerFileForDefaultContainer(File folder, String fileName) { registerFileFor(folder, ContainerManager.DEFAULT_CONTAINER, fileName); }

	/**
	 * Register a file handler to save logs
	 * @param folder The folder to save logs
	 * @param container The container to use
	 * @param fileName The name of the file to save (The result will be  SAVED_FILE_NAME-DATE.log)
	 */
	public static void registerFileFor(File folder, Container container, String fileName) {
		final File FORMATTED_FILE = new File(folder, fileName + ConsoleUtils.dateTime() + ".log");
		if(!folder.exists() && !folder.mkdirs()) throw new RuntimeException("Failed to create folder for logs: " + folder.getPath()); // Ensure folder exists, if not try to create it, if failed throw an exception

		/* Adding file handler */
		try {
            /* Set the file handler */
    		final FileHandler FILE_HANDLER = new FileHandler(FORMATTED_FILE.getPath(), true);
            FILE_HANDLER.setFormatter(new Formatter(true));
			
            /* Add the file handler to the logger */
            container.addFileHandler(FILE_HANDLER);
            
			/* Save file after closing application */
			// Runtime.getRuntime().addShutdownHook(new Thread(() -> savePreviousFile(FORMATTED_FILE), "Save_last_log_file_thread"));
        } catch (SecurityException | IOException e) {
			throw new RuntimeException("Failed to register file handler for container " + container.name(), e);
        }
	}
	
	// private static void savePreviousFile(File folder) { //TODO
	// 	// try {
    //     //     final File FILE = new File(savedFileName);
	// 	// 	Files.copy(folder.toPath(), FILE.toPath());
	// 	// } catch (IOException e) {
	// 	// 	throw new RuntimeException("Failed to save log file", e);
    //     // }
	// }
}
