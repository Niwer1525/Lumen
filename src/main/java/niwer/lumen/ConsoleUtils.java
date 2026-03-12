package niwer.lumen;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;

/**
 * @author Niwer
 */
public class ConsoleUtils {

	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd-mm-yyyy");
	private static final SimpleDateFormat DATE_TIME_FORMATTER = new SimpleDateFormat("dd-mm-yyyy_HH-mm-ss");

    /**
	 * Get the line caller of the current thread
	 * @return The line caller in the format [FileName:LineNumber] + " " (Space at the end)
	 */
	public static String getLineCaller() {
		final StackTraceElement[] STACK_TRACE = Thread.currentThread().getStackTrace();
		if (STACK_TRACE.length > 4) {
			final StackTraceElement CALLER = STACK_TRACE[4];
			return EnumLogColor.YELLOW + "[" + CALLER.getFileName() + ":" + CALLER.getLineNumber() + "] " + EnumLogColor.RESET;
		}
		return EnumLogColor.YELLOW + "[Unknown] " + EnumLogColor.RESET;
	}

	/**
	 * Convert a Throwable's stack trace to a String
	 */
	public static String stringify(Throwable t) {
		try(StringWriter STR_WRITER = new StringWriter(); PrintWriter PTRINT_WRITER = new PrintWriter(STR_WRITER)) {
			t.printStackTrace(PTRINT_WRITER);

			final String RESULT = new String(STR_WRITER.toString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
			return RESULT;
		} catch (Exception e) {
            return "Failed to stringify stack trace: " + e.getMessage();
        }
	}

	/*
	 * Get the current date
	 * @return The current date in the format dd-mm-yyyy
	 */
	public static String date() {
		return DATE_FORMATTER.format(new java.util.Date());
	}

	/**
	 * Get the current date and time
	 * @return The current date and time in the format dd-mm-yyyy_HH-mm-
	 */
	public static String dateTime() {
		return DATE_TIME_FORMATTER.format(new java.util.Date());
	}
}
