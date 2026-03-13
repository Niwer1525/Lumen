package niwer.lumen.container;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

class ContainerManagerTest {

    @Test void testRemoveDefaultHandlers() {
        ContainerManager.removeDefaultHandlers();
        final Logger ROOT_LOGGER = LogManager.getLogManager().getLogger("");
        assertEquals(0, ROOT_LOGGER.getHandlers().length);
    }

    @Test void registerContainer() {
        final Container CONTAINER = ContainerManager.registerContainer("TestContainer");
        assertEquals("TestContainer", CONTAINER.name());
    }

    @Test void registerExternalProcessor() {
        final Container CONTAINER = ContainerManager.registerContainer("TestContainer");
        Processor processor = (data, time, formattedMessage) -> {
            // Do nothing
        };
        ContainerManager.registerExternalProcessor(CONTAINER, processor);
        assertEquals(1, CONTAINER.processors().size());
    }
}
