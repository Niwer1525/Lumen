package niwer.lumen.container;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import niwer.lumen.Console;

class ContainerTest {

    @Test void testConstructor() {
        final Container CONTAINER = new Container("TestContainer");
        assertEquals("TestContainer", CONTAINER.name());
        assertNotNull(CONTAINER.handler());
        assertNotNull(CONTAINER.logger());
    }

    @Test void registerProcessor() {
        final Container CONTAINER = new Container("TestContainer");
        final Processor PROCESSOR = (data, time, formattedMessage) -> {
            // Do nothing
        };
        CONTAINER.addProcessor(PROCESSOR);

        assertEquals(1, CONTAINER.processors().size());
        assertEquals(PROCESSOR, CONTAINER.processors().iterator().next());
    }

    @Test void disablePrinting() {
        final Container CONTAINER = new Container("TestContainer");
        assertEquals(true, CONTAINER.isPrintingEnabled());

        CONTAINER.disablePrinting();
        assertEquals(false, CONTAINER.isPrintingEnabled());

        CONTAINER.enablePrinting();
        assertEquals(true, CONTAINER.isPrintingEnabled());
    }

    @Test void showNameInLogs() {
        final Container CONTAINER = new Container("TestContainer");
        assertEquals(false, CONTAINER.shouldShowNameInLogs());

        CONTAINER.showNameInLogs();
        assertEquals(true, CONTAINER.shouldShowNameInLogs());

        final String MESSAGE = (String) Console.log("Test message").container(CONTAINER).formattedMessage();
        assert MESSAGE.contains("[TestContainer]") : "Log message should contain the container. MSG : " + MESSAGE;
    }

    @Test void registerFileHandler() throws Exception {
        // final Container CONTAINER = new Container("TestContainer");
        // final FileHandler FILE_HANDLER = new FileHandler("test.log");
        // CONTAINER.addFileHandler(FILE_HANDLER);

        // assertEquals(2, CONTAINER.logger().getHandlers().length); // ConsoleHandler + FileHandler
    }
}
