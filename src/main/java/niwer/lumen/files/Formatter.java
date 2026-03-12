package niwer.lumen.files;

import java.nio.charset.StandardCharsets;
import java.util.logging.LogRecord;

import niwer.lumen.EnumLogColor;

/**
 * This class is responsible for formatting log messages for both console and file output.
 * 
 * @author Niwer
 */
public class Formatter extends java.util.logging.Formatter {

    private final boolean IS_FILE_FORMATTER;

    protected Formatter(boolean isFileFormatter) { this.IS_FILE_FORMATTER = isFileFormatter; }

    @Override
    public String format(LogRecord record) {
        if(!IS_FILE_FORMATTER)
            return record.getMessage(); // Don't format for console output, as the message is already formatted in Console.send()

        /* For files, just uncolorized the message and force it to UTF-8. */
        final String UNCOLORIZED_MESSAGE = EnumLogColor.uncolorize(record.getMessage());
        final String UTF_8_MESSAGE = new String(UNCOLORIZED_MESSAGE.getBytes(), StandardCharsets.UTF_8);
        return UTF_8_MESSAGE;
    }
}
