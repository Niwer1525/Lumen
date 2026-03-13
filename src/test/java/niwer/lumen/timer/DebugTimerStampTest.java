package niwer.lumen.timer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import niwer.lumen.Console;

class DebugTimerStampTest {

    @Test void createTimer() {
        final DebugTimeStamp TIMER = new DebugTimeStamp("test");
        assertNotNull(TIMER);
    }

    @Test void ensureTimerIsRunning() {
        final DebugTimeStamp TIMER = new DebugTimeStamp("test");
        assertTrue(TIMER.isRunning());

        /* Stop the timer and verify it's no longer running */
        TIMER.stopAndSend("Prefix message");
        assertFalse(TIMER.isRunning());
    }

    @Test void ensureElapsedTimeIsPositive() {
        final DebugTimeStamp TIMER = new DebugTimeStamp("test");
        assertTrue(TIMER.elapsedTime() >= 0);
    }

    @Test void ensureElapsedTimeIsZeroAfterStop() {
        final DebugTimeStamp TIMER = new DebugTimeStamp("test");
        TIMER.stopAndSend("Prefix message");
        assertTrue(TIMER.elapsedTime() == 0);
    }

    @Test void ensureStopWorks() {
        final DebugTimeStamp TIMER = new DebugTimeStamp("test");
        final Console CONSOLE = TIMER.stop("Elapsed time: %d ms");

        assertNotNull(CONSOLE);
        assertTrue(CONSOLE.message() instanceof String);

        final String MESSAGE = (String) CONSOLE.message();
        assertTrue(MESSAGE.contains("Elapsed time:"));
    }

    @Test void stopWithNullMessage() {
        final DebugTimeStamp TIMER = new DebugTimeStamp("test");
        final Console CONSOLE = TIMER.stop(null);

        assertNotNull(CONSOLE);
        assertNull(CONSOLE.message());
    }

    @Test void ensureRestartWorks() {
        final DebugTimeStamp TIMER = new DebugTimeStamp("test");
        final long ELAPSED_TIME_BEFORE_RESTART = TIMER.elapsedTime();
        assertTrue(ELAPSED_TIME_BEFORE_RESTART >= 0);

        TIMER.restart(); // Restart the timer and verify the elapsed time is reset

        /* Test the elapsed time after restart */
        final long ELAPSED_TIME_AFTER_RESTART = TIMER.elapsedTime();
        assertTrue(ELAPSED_TIME_AFTER_RESTART >= 0);
    }
}
