package io.github.andruid929.leutils.formatting;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Utility class for number formatting.
 * <p>
 * Provides methods to format numbers with specific decimal places and standard group separators.
 *
 * @author Andrew Jones
 * @since 3.4.0
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
    public static @NotNull String formatNumber(double number) {
        return formatter(number, 2);
    }

    /**
     * Formats a whole number to a comma-separated sequence (e.g. 1,234,567).
     *
     * @param number the numberToFormat
     * @return the formatted number string
     */

    public static @NotNull String formatWholeNumber(long number) {
        return formatter(number, 0);
    }

    /**
     * Formats a number to a string with a specified number of decimal places.
     * Uses standard group separators.
     *
     * @param number        the number to format
     * @param decimalPlaces the maximum number of decimal places (0 to 7)
     * @return the formatted number string
     */
    public static @NotNull String formatter(double number, int decimalPlaces) {
        String pattern = formatterPatternBuilder(decimalPlaces);

        return new DecimalFormat(pattern).format(number);
    }

    /**
     * Formats a decimal number as a percentage string by multiplying by 100.
     * The result is rounded to 2 decimal places using {@link RoundingMode#HALF_UP}.
     *
     * @param number the decimal number to format (e.g. "0.8725" becomes "87.25%")
     * @return the formatted percentage string with 2 decimal places and a '%' suffix
     */
    public static @NotNull String percentageDecimal(String number) {
        return new BigDecimal(number)
                .multiply(new BigDecimal("100"))
                .setScale(2, RoundingMode.HALF_UP) + "%";
    }

    /**
     * Formats a number as a percentage string without multiplication.
     * The result is rounded to 2 decimal places using {@link RoundingMode#HALF_UP}.
     *
     * @param number the number to format (e.g. "87.25" becomes "87.25%")
     * @return the formatted percentage string with 2 decimal places and a '%' suffix
     */
    public static @NotNull String percentage(String number) {
        return new BigDecimal(number)
                .setScale(2, RoundingMode.HALF_UP) + "%";
    }

    /**
     * Builds a {@link DecimalFormat} pattern string for the given number of decimal places.
     *
     * @param decimalPlaces the maximum number of decimal places
     * @return the pattern string (e.g. "#,###.##" for 2 decimal places)
     * @throws IllegalArgumentException if decimalPlaces is negative
     * @apiNote Upper bound of decimal places is 7 because if you need more than that,
     * you need precision more than formatting.
     */
    static @NotNull String formatterPatternBuilder(@Range(from = 0, to = 7) int decimalPlaces) {
        if (decimalPlaces == 0) {
            return "#,###";
        }

        return "#,###." + "#".repeat(decimalPlaces);
    }
}
