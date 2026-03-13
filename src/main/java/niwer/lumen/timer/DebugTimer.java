package niwer.lumen.timer;

/**
 * @author Niwer
 */
public class DebugTimer {

    /**
     * Starts a timer with the threadName:default as key.
     * 
     * @return The created timer, that can be stopped later to log the elapsed time since the creation of the timer.
     */
    public static DebugTimeStamp startTimer() { return startTimer("default"); }

    /**
     * Starts a timer with the threadName:timerName as key.
     * 
     * @param timerName The name of the timer. Will make the timer identfiable as : <code>threadName:timerName</code>.
     * @return The created timer, that can be stopped later to log the elapsed time since the creation of the timer.
     */
    public static DebugTimeStamp startTimer(String timerName) {
        final String TIMER_KEY = Thread.currentThread().getName() + ":" + timerName;
        if(DebugTimeStamp.TIME_STAMPS.containsKey(TIMER_KEY)) return DebugTimeStamp.TIME_STAMPS.get(TIMER_KEY).restart(); // If timer already exists, restart it and return it
        return new DebugTimeStamp(TIMER_KEY);
    }
}