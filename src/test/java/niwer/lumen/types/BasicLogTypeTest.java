package niwer.lumen.types;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import niwer.lumen.EnumLogColor;

class BasicLogTypeTest {

    @Test void testConstructor() {
        BasicLogType logType = new BasicLogType("INFO", EnumLogColor.BLUE);
        assertEquals("INFO", logType.name());
        assertEquals(EnumLogColor.BLUE, logType.color());
    }

    @Test void testConstructorWithNullOrEmptyValues() {
        assertThrows(IllegalArgumentException.class, () -> new BasicLogType("", EnumLogColor.BLUE));
        assertThrows(IllegalArgumentException.class, () -> new BasicLogType(null, EnumLogColor.BLUE));
        assertThrows(IllegalArgumentException.class, () -> new BasicLogType("INFO", null));
    }

    @Test void testDefaultValues() {
        assertNotNull(DefaultLogTypes.INFO);
        assertNotNull(DefaultLogTypes.NONE);
    }
}
