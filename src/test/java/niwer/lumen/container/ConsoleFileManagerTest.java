package niwer.lumen.container;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.Test;

class ConsoleFileManagerTest {

    @Test void registerFileForDefaultContainer() {
        final File FOLDER = new File("logs");
        ConsoleFileManager.registerFileForDefaultContainer(FOLDER, "test");
        // We can't really test if the file handler is working without checking the file system, but we can at least check if the file handler is registered to the default container
        assertEquals(2, ContainerManager.DEFAULT_CONTAINER.logger().getHandlers().length);
    }

    @Test void registerFileFor() {
        final File FOLDER = new File("logs");
        final Container CONTAINER = ContainerManager.registerContainer("TestContainer");
        ConsoleFileManager.registerFileFor(FOLDER, CONTAINER, "test");
        // We can't really test if the file handler is working without checking the file system, but we can at least check if the file handler is registered to the container
        assertEquals(2, CONTAINER.logger().getHandlers().length);
    }
}
