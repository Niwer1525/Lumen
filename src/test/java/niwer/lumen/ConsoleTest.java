package niwer.lumen;

import org.junit.jupiter.api.Test;

class ConsoleTest {

    @Test void testLog() {
        Console.log("Hello %s", "world").send();
        Console.log(new Exception("Test exception")).send();
        Console.log("This is an error message with %s", new Exception("Test exception")).send();
    }
}
