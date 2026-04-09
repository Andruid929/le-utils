package io.github.andruid929.leutils.data;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import io.github.andruid929.leutils.formatting.NumberFormatting;

/**
 * Utility class for converting between different data units.
 * <p>
 * Provides methods for performing conversions using both {@code long} and {@code double} types,
 * as well as formatted string outputs.
 *
 * @author Andrew Jones
 * @since 3.4.0
 */
public final class DataUnitConversion {

    private DataUnitConversion() {
    }

    /**
     * Converts a value from one unit to another using {@code long} precision.
     * <p>
     * <b>Note on Overflow:</b> This method uses standard Java {@code long} arithmetic.
     * If the converted value exceeds {@link Long#MAX_VALUE}, it will wrap around
     * according to two's complement behaviour, potentially resulting in a negative value.
     * This behaviour is intentional to maintain performance and avoid throwing exceptions on
     * potentially unsafe user input.
     *
     * @param value the numeric value to convert
     * @param from  the source unit
     * @param to    the target unit
     * @return the converted value in the target unit
     * @throws IllegalArgumentException if {@code value} is negative
     */
    @Contract(pure = true)
    public static long calculate(long value, DataUnit from, DataUnit to) {
        if (value < 0) {
            throw new IllegalArgumentException("Negative data is not possible");
        }

        return from.convertTo(value, to);
    }

    /**
     * Converts a value from one unit to another using {@code double} precision.
     *
     * @param value the numeric value to convert
     * @param from  the source unit
     * @param to    the target unit
     * @return the converted value in the target unit
     * @throws IllegalArgumentException if {@code value} is negative
     */

    @Contract(pure = true)
    public static double calculate(double value, DataUnit from, DataUnit to) {
        if (value < 0) {
            throw new IllegalArgumentException("Negative data is not possible");
        }

        return from.convertTo(value, to);
    }

    /**
     * Converts a {@code long} value from one unit to another and returns the result as a {@code double}.
     *
     * @param value the numeric value to convert
     * @param from  the source unit
     * @param to    the target unit
     * @return the converted value in the target unit as a {@code double}
     * @throws IllegalArgumentException if {@code value} is negative
     */

    @Contract(pure = true)
    public static double calculateAsDouble(long value, DataUnit from, DataUnit to) {
        if (value < 0) {
            throw new IllegalArgumentException("Negative data is not possible");
        }

        double valueAsDouble = (double) value;

        return from.convertTo(valueAsDouble, to);
    }

    /**
     * Converts a value and returns it as a formatted string with the target unit suffix.
     *
     * @param value the numeric value to convert
     * @param from  the source unit
     * @param to    the target unit
     * @return a formatted string (e.g. "1,024B")
     * @throws IllegalArgumentException if {@code value} is negative
     */
    public static @NotNull String formatWithUnit(long value, DataUnit from, DataUnit to) {
        long calculation = calculate(value, from, to);

        return NumberFormatting.formatWholeNumber(calculation).concat(to.getUnitSuffix());
    }

    /**
     * Converts a value and returns it as a formatted string with the target unit suffix.
     *
     * @param value    the numeric value to convert
     * @param from     the source unit
     * @param to       the target unit
     * @param roundOff optional boolean; if {@code true}, rounds the result to 2 decimal places using
     *                 {@link io.github.andruid929.leutils.formatting.NumberFormatting#formatNumber(double)}
     * @return a formatted string
     * @throws IllegalArgumentException if {@code value} is negative
     */
    @Contract(pure = true)
    public static @NotNull String formatWithUnit(double value, DataUnit from, DataUnit to, boolean @NotNull ... roundOff) {
        double calculation = calculate(value, from, to);

        boolean shouldRoundOff = (roundOff.length > 0) && roundOff[0];

        if (shouldRoundOff) {
            return NumberFormatting.formatNumber(calculation).concat(to.getUnitSuffix());
        }

        return String.valueOf(calculation).concat(to.getUnitSuffix());
    }
}
