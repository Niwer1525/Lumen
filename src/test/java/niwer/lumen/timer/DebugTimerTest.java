package niwer.lumen.timer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DebugTimerTest {

    @Test void testTimer() throws InterruptedException {
        final DebugTimeStamp TIMER = DebugTimer.startTimer();
        Thread.sleep(1000);
        TIMER.stopAndSend("This is a Prefix message");
    }

    @Test void testMultipleSameTimers() throws InterruptedException {
        final DebugTimeStamp TIMER1 = DebugTimer.startTimer("test");
        Thread.sleep(1000);
        final DebugTimeStamp TIMER2 = DebugTimer.startTimer("test");
        Thread.sleep(1000);

        assertEquals(TIMER1, TIMER2); // Should be the same timer.

        /* Stop timers and check time */
        TIMER1.stopAndSend("This is a Prefix message for Timer 2");
        TIMER2.stopAndSend("This is a Prefix message for Timer 2");

        assertEquals(TIMER1.elapsedTime(), TIMER2.elapsedTime());

        assertEquals(0, TIMER1.elapsedTime()); // Since TIMER2 restarted TIMER1, the elapsed time should be 0.
    }
}
