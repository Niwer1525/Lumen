package niwer.lumen.timer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import niwer.lumen.Console;

/**
 * @author Niwer
 */
public class DebugTimeStamp {
    protected static final Map<String, DebugTimeStamp> TIME_STAMPS = new ConcurrentHashMap<>(); // Need to be thread safe since it can be accessed from multiple threads

    private final String name;
    private long startTime;
    private boolean isRunning = true;

    protected DebugTimeStamp(String name) {
        this.name = name;
        this.isRunning = true;
        this.startTime = System.currentTimeMillis();
        TIME_STAMPS.put(this.name, this); // Add the timer to the map with the name as key
    }

    /**
     * Restart the current timer. Will reset the start time.
     * @return The current timer to be chained.
     */
    public DebugTimeStamp restart() {
        this.isRunning = true;
        this.startTime = System.currentTimeMillis();
        return this;
    }

    /**
     * Get the elapsed time since the creation of the timer in milliseconds.
     * If the timer is stopped, the elapsed time is 0.
     * @return The elapsed time in milliseconds.
     */
    public long elapsedTime() {
        if(!isRunning) return 0; // If not running, return 0 (since the timer is stopped, the elapsed time is 0)
        return System.currentTimeMillis() - startTime;
    }

    /**
     * Check if the timer is still running.
     * @return True if the timer is still running, false otherwise.
     */
    public boolean isRunning() { return this.isRunning; }

    /**
     * Stops the current timer and returns a Console with the message and elapsed time since the creation of the time.
     * If the timer is already stopped, the function will return an empty Console so nothing can and will be printed.
     * 
     * @param message The message used to format the log. The message can contain a "%d" placeholder that will be replaced by the elapsed time in milliseconds.
     * @return The console object empty or not depending on the state of the timer, that can be sent to print the log with the elapsed time.
     */
    public Console stop(String message) {
        if(!isRunning) return Console.log((Object)null); // If not running, do nothing (return empty log, that won't be printed)

        isRunning = false;
        final long ELAPSED_TIME = System.currentTimeMillis() - startTime;
        return Console.log(message == null ? null : String.format(message, ELAPSED_TIME)); // If message is null, return an empty log, that won't be printed
    }

    /**
     * Stops the current timer and sends the log with the message and elapsed time since the creation of the time.
     * This function will automatically print the message to the console, so there is no need to call the send() method after it.
     * If the timer is already stopped, the function will do nothing. (No printing, no error, just return)
     * 
     * @param message The message used to format the log. The message can contain a "%d" placeholder that will be replaced by the elapsed time in milliseconds.
     */
    public void stopAndSend(String message) { stop(message).send(); }
}