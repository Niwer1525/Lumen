package niwer.lumen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

import niwer.lumen.container.Container;
import niwer.lumen.container.ContainerManager;
import niwer.lumen.types.DefaultLogTypes;

class ConsoleTest {

    @Test void testLog() {
        Console.log("Hello %s", "world").send();
        Console.log(new Exception("Test exception")).send();
        Console.log("This is an error message", new Exception("Test exception")).send();
    }

    @Test void testGetters() {
        Console console = Console.log("Hello %s", "world").file(new File("Path/to/File.png"));
        assertEquals("Hello world", console.message());
        assertEquals(DefaultLogTypes.NONE, console.type());
        assertFalse(console.isError());
        assertNotNull(console.file());

        /* Container */
        assertNotNull(console.container());
        assertEquals(ContainerManager.DEFAULT_CONTAINER, console.container());
    }

    @Test void testType() {
        Console console = Console.log("Hello %s", "world").type(DefaultLogTypes.INFO);
        assertEquals(DefaultLogTypes.INFO, console.type());
        assertThrows(IllegalArgumentException.class, () -> console.type(null));
    }

    @Test void testContainer() {
        final Container MY_CONTAINER = ContainerManager.registerContainer("MyContainer");
        Console console = Console.log("Hello %s", "world").container(MY_CONTAINER);
        assertEquals(MY_CONTAINER, console.container());

        console.container(null); // Should reset to default container
        assertEquals(ContainerManager.DEFAULT_CONTAINER, console.container());
    }

    @Test void testProcessors() {
        final Container MY_CONTAINER = ContainerManager.registerContainer("MyContainer");
        ContainerManager.registerExternalProcessor(MY_CONTAINER, (data, time, formattedMessage) -> {
            assertTrue(true); // Just to check if the processor is called
        });

        final Console CONSOLE = Console.log("Hello %s", "world").container(MY_CONTAINER).sendToProcessor();
        CONSOLE.send();
        assertEquals("Hello world", CONSOLE.message());
    }

    @Test void testNullMessage() {
        Console console = Console.log((String) null);
        assertNull(console.message());
        console.send(); // Should do nothing
    }
}
