package io.github.andruid929.leutils.stringutil;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import io.github.andruid929.leutils.strings.StringUtil;

/**
 * Utility class for character-level string manipulations such as trimming and slicing.
 *
 * @author Andrew
 * @since 4.4.0
 */
public final class StringFormatter {

    private static boolean interpolateFailsOnNull = true;

    private StringFormatter() {
    }

    /**
     * Removes the first and last characters from the input, after trimming surrounding whitespace.
     * Returns the trimmed input unchanged if its length is less than 2.
     *
     * @param input non-null input string
     * @return trimmed input without the first and last characters, or the trimmed input if not applicable
     */

    public static @NotNull String trimCharacters(@NotNull String input) {
        String trimmedString = input.trim();

        if (trimmedString.isBlank()) {
            return trimmedString;
        }

        int lastCharacterIndex = StringUtil.getLastCharIndex(trimmedString);

        if (lastCharacterIndex < 1) {
            return trimmedString;
        }

        return trimmedString.substring(1, lastCharacterIndex);
    }

    /**
     * Removes {@code charsToTrim} characters from both the start and the end of the input,
     * after trimming surrounding whitespace.
     * If {@code charsToTrim <= 0} or the operation would over-trim, returns the trimmed input unchanged.
     *
     * @param input       non-null input string
     * @param charsToTrim number of characters to remove from both ends
     * @return the trimmed-and-sliced substring, or the trimmed input if not applicable
     */

    public static @NotNull String trimCharacters(@NotNull String input, int charsToTrim) {
        String trimmedString = input.trim();

        if (trimmedString.isBlank() || charsToTrim <= 0) {
            return trimmedString;
        }

        int length = trimmedString.length();
        int endIndex = length - charsToTrim;

        if (charsToTrim >= endIndex) {
            return trimmedString;
        }

        return trimmedString.substring(charsToTrim, endIndex);
    }

    /**
     * Conditionally removes a specific prefix and suffix (as strings) from the input,
     * after trimming surrounding whitespace. If both {@code startChars} and {@code endChars}
     * are blank, the trimmed input is returned unchanged.
     *
     * @param input      non-null input string
     * @param startChars prefix to remove if present (Can be blank)
     * @param endChars   suffix to remove if present (Can be blank)
     * @return the trimmed-and-sliced substring if both prefix and suffix match; otherwise the trimmed input
     */

    public static @NotNull String trimCharacters(@NotNull String input, @NotNull String startChars, @NotNull String endChars) {
        String trimmedString = input.trim();

        if (trimmedString.isBlank() || trimmedString.length() < 2
                || (startChars.isBlank() && endChars.isBlank())) {
            return trimmedString;
        }

        int length = trimmedString.length();

        if (startChars.length() > length || endChars.length() > length) {
            return trimmedString;
        }

        int lastValidIndex = length - endChars.length();

        if (trimmedString.startsWith(startChars) && trimmedString.endsWith(endChars)) {
            return trimmedString.substring(startChars.length(), lastValidIndex);
        }

        return trimmedString;
    }

    /**
     * Conditionally removes the first and last characters if they match {@code firstChar} and {@code lastChar},
     * after trimming surrounding whitespace. Otherwise, returns the trimmed input.
     *
     * @param input     non-null input string
     * @param firstChar expected first character
     * @param lastChar  expected last character
     * @return the trimmed-and-sliced substring or the trimmed input if not applicable
     */

    public static @NotNull String trimCharacters(@NotNull String input, char firstChar, char lastChar) {
        String trimmedString = input.trim();

        if (trimmedString.isBlank()) {
            return trimmedString;
        }

        int lastCharacterIndex = trimmedString.length() - 1;

        if (trimmedString.charAt(0) == firstChar && trimmedString.charAt(lastCharacterIndex) == lastChar) {
            return trimmedString.substring(1, lastCharacterIndex);
        }

        return trimmedString;
    }

    public static void setInterpolateFailsOnNull(boolean failsOnNull) {
        interpolateFailsOnNull = failsOnNull;
    }

    public static String interpolate(@NotNull String s, Object ... args) {
        if (args.length == 0) {
            return s;
        }

        String interpolatedString = s;

        for (Object arg : args) {
            if (arg == null && interpolateFailsOnNull) {
                throw new IllegalArgumentException("Interpolation failed due to null argument");
            }

            if (arg == null) {
                interpolatedString = interpolatedString.replaceFirst(Pattern.quote("{}"), "null");
            } else {
                interpolatedString = interpolatedString.replaceFirst(Pattern.quote("{}"), arg.toString());
            }
        }

        return interpolatedString;
    }
}
