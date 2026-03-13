package niwer.lumen;

import org.junit.jupiter.api.Test;

class ConsoleUtilsTest {
    @Test void testGetLineCaller() {
        String lineCaller = ConsoleUtils.getLineCaller();
        System.err.println(lineCaller);
        assert lineCaller.contains("Method.java:");
    }

    @Test void testStringify() {
        Exception e = new Exception("Test exception");
        String result = ConsoleUtils.stringify(e);
        assert result.contains("Test exception");
        assert result.contains("at niwer.lumen.ConsoleUtilsTest.testStringify(ConsoleUtilsTest.java:");
    }

    @Test void testDate() {
        String date = ConsoleUtils.date();
        assert date.matches("\\d{2}-\\d{2}-\\d{4}");
    }

    @Test void testDateTime() {
        String dateTime = ConsoleUtils.dateTime();
        assert dateTime.matches("\\d{2}-\\d{2}-\\d{4}_\\d{2}-\\d{2}-\\d{2}");
    }
}
