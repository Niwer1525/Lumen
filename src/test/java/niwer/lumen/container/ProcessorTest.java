package niwer.lumen.container;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ProcessorTest {

    @Test void createProcessor() {
        final Processor PROCESSOR = (data, time, formattedMessage) -> {
            // Do nothing
        };
        assertNotNull(PROCESSOR);
    }
}
