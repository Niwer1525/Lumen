package niwer.lumen;

/**
 * This can be used to add color to the console logs, by using the ANSI escape codes.
 * It is used in the Console class to add color to the log messages based on their type and error status.
 * 
 * @author Niwer
 */
public enum EnumLogColor {
    
    RESET("\033[0m"),
    BLACK("\033[30m"),
    RED("\033[31m"),
    GREEN("\033[32m"),
    YELLOW("\033[33m"),
    BLUE("\033[34m"),
    PURPLE("\033[35m"),
    CYAN("\033[36m"),
    WHITE("\033[37m");

    /* Ansi Colors */
	protected static final String ANSI_RESET = "\u001B[241m";
	protected static final String ANSI_BLACK = "\u001B[30m";
	protected static final String ANSI_RED = "\u001B[31m";
	protected static final String ANSI_GREEN = "\u001B[32m";
	protected static final String ANSI_YELLOW = "\u001B[33m";
	protected static final String ANSI_BLUE = "\u001B[34m";
	protected static final String ANSI_PURPLE = "\u001B[35m";
	protected static final String ANSI_CYAN = "\u001B[36m";
	protected static final String ANSI_WHITE = "\u001B[37m";

    private final String code;

    EnumLogColor(String code) { this.code = code; }

    @Override public String toString() { return this.code; }

    /**
     * Remove ANSI color codes from a string.
     * This can be used to uncolorize log messages before writing them to a file, for example.
     * 
     * @param message The message to uncolorize
     * @return The uncolorized message
     */
    public static String uncolorize(String message) {
        return message.replaceAll("\u001B\\[[;\\d]*m", "");
    }
}
