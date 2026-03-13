package niwer.lumen.container;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.logging.FileHandler;

import org.junit.jupiter.api.Test;

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

    @Test void registerFileHandler() throws Exception {
        // final Container CONTAINER = new Container("TestContainer");
        // final FileHandler FILE_HANDLER = new FileHandler("test.log");
        // CONTAINER.addFileHandler(FILE_HANDLER);

        // assertEquals(2, CONTAINER.logger().getHandlers().length); // ConsoleHandler + FileHandler
    }
}
