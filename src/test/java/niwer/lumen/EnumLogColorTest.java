package niwer.lumen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class EnumLogColorTest {
    @Test void testUncolorize() {
        final  String COLORED_MSG = EnumLogColor.RED + "This is a red message." + EnumLogColor.RESET;
        final String UNCOLORED_MSG = EnumLogColor.uncolorize(COLORED_MSG);
        assertEquals("This is a red message.", UNCOLORED_MSG);
    }
}
