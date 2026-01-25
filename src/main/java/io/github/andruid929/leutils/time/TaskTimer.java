package io.github.andruid929.leutils.time;

import org.jetbrains.annotations.NotNull;

/**
 * A simple timer utility for measuring elapsed time of tasks or operations.
 * <p>
 * This class captures the start time upon instantiation and provides methods to
 * calculate elapsed time in various units. The timer starts immediately when the
 * object is created.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * TaskTimer timer = new TaskTimer();
 * // ... perform some task ...
 * double elapsedSeconds = timer.elapsedTime(TimeUnitConversion.Unit.SECONDS);
 * String formatted = timer.formatElapsedTime(TimeUnitConversion.Unit.MILLISECONDS);
 * }</pre>
 *
 * @author Andrew Jones
 * @since 3.0.0
 * @apiNote <strong>Thread Safety:</strong> This class is designed for single-threaded use only.
 * It should not be shared across multiple threads without
 * external synchronisation.
 */
public final class TaskTimer {

    /**
     * The start time in nanoseconds, captured when this timer was created.
     */
    private final long start;

    /**
     * Creates a new TaskTimer and starts timing immediately.
     * The start time is captured using {@link System#nanoTime()}.
     */
    public TaskTimer() {
        start = System.nanoTime();
    }

    /**
     * Returns the elapsed time in nanoseconds since this timer was created.
     *
     * @return the elapsed time in nanoseconds.
     */
    public long elapsedNanoTime() {
        long now = System.nanoTime();

        return now - start;
    }

    /**
     * Returns the elapsed time in the specified time unit.
     * The elapsed time is first calculated in {@link #elapsedNanoTime() nanoseconds}
     * and then converted to the requested unit.
     *
     * @param convertTo the target time unit for the elapsed time.
     * @return the elapsed time in the specified unit.
     */
    public double elapsedTime(@NotNull TimeUnitConversion.Unit convertTo) {
        double durationInMs = (double) elapsedNanoTime() / 1_000_000.0;

        return TimeUnitConversion.calculate(durationInMs, TimeUnitConversion.Unit.MILLISECONDS, convertTo);
    }

    /**
     * Returns a formatted string representation of the elapsed time in the specified unit.
     * The format includes the elapsed time value rounded to 3 decimal places followed by
     * the unit suffix.
     *
     * @param convertTo the time unit to use for formatting.
     * @return a formatted string in the format <strong>value[unit]</strong> (e.g. "123.456ms").
     */
    public @NotNull String formatElapsedTime(@NotNull TimeUnitConversion.Unit convertTo) {
        double elapsedTime = elapsedTime(convertTo);

        return String.format("%.3f%s", elapsedTime, convertTo.getUnitSuffix());
    }

}
