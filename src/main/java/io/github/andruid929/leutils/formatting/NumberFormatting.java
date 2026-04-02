package io.github.andruid929.leutils.formatting;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.text.DecimalFormat;

/**
 * Utility class for number formatting.
 * <p>
 * Provides methods to format numbers with specific decimal places and standard group separators.
 *
 * @since 3.4.0
 * @author Andrew Jones
 */
public final class NumberFormatting {

    private NumberFormatting() {
    }

    /**
     * Formats a number to a string with a maximum of 2 decimal places.
     * Uses standard group separators (e.g. 1,234.56).
     *
     * @param number the number to format
     * @return the formatted number string
     */
    public static @NotNull String formatNumber(Number number) {
        return formatter(number, 2);
    }

    /**
     * Formats a number to a string with a specified number of decimal places.
     * Uses standard group separators.
     *
     * @param number        the number to format
     * @param decimalPlaces the maximum number of decimal places (0 to 7)
     * @return the formatted number string
     */
    public static @NotNull String formatter(Number number, int decimalPlaces) {
        String pattern = formatterPatternBuilder(decimalPlaces);

        return new DecimalFormat(pattern).format(number);
    }

    /**
     * Builds a {@link DecimalFormat} pattern string for the given number of decimal places.
     *
     * @param decimalPlaces the maximum number of decimal places
     * @return the pattern string (e.g. "#,###.##" for 2 decimal places)
     * @throws IllegalArgumentException if decimalPlaces is negative
     */
    static @NotNull String formatterPatternBuilder(@Range(from = 0, to = 7) int decimalPlaces) {
        if (decimalPlaces == 0) {
            return "#,###";
        }

        return "#,###." + "#".repeat(decimalPlaces);
    }
}
