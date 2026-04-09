package io.github.andruid929.leutils.time;

import org.jetbrains.annotations.NotNull;
/**
 * Utility class for converting between different time units.
 * <p>
 * This class provides convenience methods for common time conversions
 * (milliseconds to seconds, seconds to minutes, etc.) as well as a general
 * conversion method that works with any {@link TimeUnit}.
 * </p>
 *
 * @author Andrew Jones
 * @since 2.2.0
 */
public class TimeUnitConversion {
    
    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private TimeUnitConversion() {
    }

    /**
     * Converts milliseconds to seconds.
     *
     * @param millis the time value in milliseconds.
     * @return the time value in seconds.
     * @throws IllegalArgumentException if {@code millis} is negative.
     */
    public static double milliToSecond(double millis) {
        return calculate(millis, TimeUnit.MILLISECONDS, TimeUnit.SECONDS);
    }

    /**
     * Converts seconds to minutes.
     *
     * @param seconds the time value in seconds.
     * @return the time value in minutes.
     * @throws IllegalArgumentException if {@code seconds} is negative.
     */
    public static double secondToMinute(double seconds) {
        return calculate(seconds, TimeUnit.SECONDS, TimeUnit.MINUTES);
    }

    /**
     * Converts minutes to hours.
     *
     * @param minutes the time value in minutes.
     * @return the time value in hours.
     * @throws IllegalArgumentException if {@code minutes} is negative.
     */
    public static double minuteToHour(double minutes) {
        return calculate(minutes, TimeUnit.MINUTES, TimeUnit.HOURS);
    }

    /**
     * Converts hours to days.
     *
     * @param hours the time value in hours.
     * @return the time value in days.
     * @throws IllegalArgumentException if {@code hours} is negative.
     */
    public static double hourToDay(double hours) {
        return calculate(hours, TimeUnit.HOURS, TimeUnit.DAYS);
    }

    /**
     * Converts milliseconds to seconds.
     *
     * @param millis the time value in milliseconds.
     * @return the time value in seconds.
     * @throws IllegalArgumentException if {@code millis} is negative
     * @apiNote This method performs integer division which results in
     * precision loss. Any fractional seconds are truncated. For example, 1500 milliseconds
     * will be converted to 1 second, losing the remaining 500 milliseconds.
     * Consider using {@link #milliToSecond(double)} for more precise conversions.
     * <p>
     * <b>Warning:</b> This method may overflow for large values if multiplication of
     * {@code millis} by the conversion factor exceeds {@link Long#MAX_VALUE}.
     */
    public static long milliToSecond(long millis) {
        return calculate(millis, TimeUnit.MILLISECONDS, TimeUnit.SECONDS);
    }

    /**
     * Converts seconds to minutes.
     *
     * @param seconds the time value in seconds.
     * @return the time value in minutes.
     * @throws IllegalArgumentException if {@code seconds} is negative.
     * @apiNote This method performs integer division which results in
     * precision loss. Any fractional minutes are truncated. For example, 90 seconds
     * will be converted to 1 minute, losing the remaining 30 seconds.
     * Consider using {@link #secondToMinute(double)} for more precise conversions.
     * <p>
     * <b>Warning:</b> This method may overflow for large values if multiplication of
     * {@code seconds} by the conversion factor exceeds {@link Long#MAX_VALUE}.
     */
    public static long secondToMinute(long seconds) {
        return calculate(seconds, TimeUnit.SECONDS, TimeUnit.MINUTES);
    }

    /**
     * Converts minutes to hours.
     *
     * @param minutes the time value in minutes.
     * @return the time value in hours.
     * @throws IllegalArgumentException if {@code minutes} is negative.
     * @apiNote This method performs integer division which results in
     * precision loss. Any fractional hours are truncated. For example, 90 minutes
     * will be converted to 1 hour, losing the remaining 30 minutes.
     * Consider using {@link #minuteToHour(double)} for more precise conversions.
     * <p>
     * <b>Warning:</b> This method may overflow for large values if multiplication of
     * {@code minutes} by the conversion factor exceeds {@link Long#MAX_VALUE}.
     */
    public static long minuteToHour(long minutes) {
        return calculate(minutes, TimeUnit.MINUTES, TimeUnit.HOURS);
    }

    /**
     * Converts hours to days.
     *
     * @param hours the time value in hours.
     * @return the time value in days.
     * @throws IllegalArgumentException if {@code hours} is negative.
     * @apiNote This method performs integer division which results in
     * precision loss. Any fractional days are truncated. For example, 30 hours
     * will be converted to 1 day, losing the remaining 6 hours.
     * Consider using {@link #hourToDay(double)} for more precise conversions.
     * <p>
     * <b>Warning:</b> This method may overflow for large values if multiplication of
     * {@code hours} by the conversion factor exceeds {@link Long#MAX_VALUE}.
     */
    public static long hourToDay(long hours) {
        return calculate(hours, TimeUnit.HOURS, TimeUnit.DAYS);
    }

    /**
     * Converts a time value from one unit to another.
     * <p>
     * This method first converts the input value to milliseconds using the source unit,
     * then converts from milliseconds to the target unit.
     * </p>
     *
     * @param value the time value to convert.
     * @param from  the source time unit.
     * @param to    the target time unit.
     * @return the converted time value in the target unit.
     * @throws IllegalArgumentException if {@code value} is negative.
     */
    public static double calculate(double value, TimeUnit from, TimeUnit to) {
        if (value < 0) {
            throw new IllegalArgumentException("Negative time is not possible");
        } else if (value == 0) {
            return 0;
        }

        double valueInMs = value * from.getInMillis();

        return valueInMs / to.getInMillis();
    }

    /**
     * Converts a time value from one unit to another.
     * <p>
     * This method first converts the input value to milliseconds using the source unit,
     * then converts from milliseconds to the target unit.
     * </p>
     *
     * @param value the time value to convert.
     * @param from  the source time unit.
     * @param to    the target time unit.
     * @return the converted time value in the target unit.
     * @throws IllegalArgumentException if {@code value} is negative.
     * @apiNote This method performs integer division which results in
     * precision loss. Any fractional values in the target unit are truncated.
     * For example, converting 1500 milliseconds to seconds will result in 1 second,
     * losing the remaining 500 milliseconds. Consider using {@link #calculate(double, TimeUnit, TimeUnit)}
     * for more precise conversions.
     * <p>
     * <b>Warning:</b> This method may overflow for large values if multiplication of
     * {@code value} by the conversion factor exceeds {@link Long#MAX_VALUE}.
     */
    public static long calculate(long value, TimeUnit from, TimeUnit to) {
        if (value < 0) {
            throw new IllegalArgumentException("Negative time is not possible");
        } else if (value == 0) {
            return 0;
        }

        long valueInMs = value * from.getInMillis();

        return valueInMs / to.getInMillis();
    }

    /**
     * Converts a {@code long} value from one time unit to another and returns the result as a {@code double}.
     *
     * @param value the numeric value to convert
     * @param from  the source unit
     * @param to    the target unit
     * @return the converted value in the target unit as a {@code double}
     * @throws IllegalArgumentException if {@code value} is negative
     * @since 3.4.0
     */
    public static double calculateAsDouble(long value, TimeUnit from, TimeUnit to) {
        if (value < 0) {
            throw new IllegalArgumentException("Negative time is not possible");
        } else if (value == 0) {
            return 0;
        }

        double valueInMs = value * from.getInMillis();

        return valueInMs / to.getInMillis();
    }

    /**
     * Converts a time value from one unit to another and appends the target unit's suffix
     * to the resulting value.
     * <p>It is important to note that this method calls {@link #calculate(long, TimeUnit, TimeUnit) calculate()} which means any problems with that
     * method will also be present here.</p>
     *
     * @param value the time value to convert.
     * @param from  the source time unit.
     * @param to    the target time unit.
     * @return a String representation of the converted time value concatenated with the target unit's suffix.
     * @throws IllegalArgumentException if {@code value} is negative.
     */
    public static @NotNull String formatWithUnit(long value, TimeUnit from, TimeUnit to) {
        long calculation = calculate(value, from, to);

        return String.valueOf(calculation).concat(to.getUnitSuffix());
    }

    /**
     * Converts a time value from one unit to another, formats the result, and appends the target unit's suffix.
     * <p>
     * The method allows optional rounding of the converted value. If rounding is enabled, the result is formatted
     * to three decimal places before appending the unit's suffix.
     *
     * @param value    the time value to be converted.
     * @param from     the source unit of the input value.
     * @param to       the target unit for the output value.
     * @param roundOff an optional boolean argument indicating whether to round the result to three decimal places.
     *                 If omitted or false, no rounding is applied.
     * @return a String representation of the converted value, optionally rounded, concatenated with the target unit's suffix.
     * @throws IllegalArgumentException if {@code value} is negative.
     */
    public static @NotNull String formatWithUnit(double value, TimeUnit from, TimeUnit to, boolean @NotNull ... roundOff) {
        double calculation = calculate(value, from, to);

        boolean shouldRound = roundOff.length > 0 && roundOff[0];

        if (shouldRound) {
            return String.format("%.3f%s", calculation, to.getUnitSuffix());
        }

        return String.valueOf(calculation).concat(to.getUnitSuffix());
    }

}
