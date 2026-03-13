package niwer.lumen.container;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.logging.LogRecord;

import org.junit.jupiter.api.Test;

import niwer.lumen.EnumLogColor;

class FormatterTest {

    @Test void testConstructorAndFormat() {
        final Formatter FORMATTER = new Formatter(true);
        final Formatter FORMATTER_2 = new Formatter(false);

        final String TEST_MESSAGE = EnumLogColor.RED + "This is a test message.";
        final LogRecord LOG_RECORD = new LogRecord(java.util.logging.Level.INFO, TEST_MESSAGE);

        /* Test formatting */
        final String FORMATTED_MSG = FORMATTER.format(LOG_RECORD);
        final String FORMATTED_MSG_2 = FORMATTER_2.format(LOG_RECORD);

        assertEquals("This is a test message.\n", FORMATTED_MSG);
        assertEquals("\033[31m" + "This is a test message.\n", FORMATTED_MSG_2);
    }
}
