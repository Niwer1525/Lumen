package niwer.lumen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import niwer.lumen.container.Container;
import niwer.lumen.container.Processor;

class LumenEngineTest {

    @Test void testRemoveDefaultHandlers() {
        LumenEngine.removeDefaultHandlers();
        final Logger ROOT_LOGGER = LogManager.getLogManager().getLogger("");
        assertEquals(0, ROOT_LOGGER.getHandlers().length);
    }

    @Test void registerContainer() {
        final Container CONTAINER = LumenEngine.registerContainer("TestContainer");
        assertEquals("TestContainer", CONTAINER.name());
    }

    @Test void registerExternalProcessor() {
        final Container CONTAINER = LumenEngine.registerContainer("TestContainer");
        Processor processor = (data, time, formattedMessage) -> {
            // Do nothing
        };
        LumenEngine.registerExternalProcessor(CONTAINER, processor);
        assertEquals(1, CONTAINER.processors().size());
    }

    @Test void registerExternalProcessorForDefaultContainer() {
        Processor processor = (data, time, formattedMessage) -> {
            // Do nothing
        };
        LumenEngine.registerExternalProcessorForDefaultContainer(processor);
        assertEquals(1, LumenEngine.DEFAULT_CONTAINER.processors().size());
    }

    @Test void disablePrintingFromDefaultContainer() {
        LumenEngine.disablePrintingFromDefaultContainer();
        assertEquals(false, LumenEngine.DEFAULT_CONTAINER.isPrintingEnabled());

        LumenEngine.DEFAULT_CONTAINER.enablePrinting(); // Re-enable printing for other tests
    }
}
