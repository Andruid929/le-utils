package io.github.andruid929.leutils.stringutil;

import org.jetbrains.annotations.NotNull;

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

        int lastCharacterIndex = trimmedString.length() - 1;

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

    /**
     * Configures whether interpolation should fail when any supplied argument is {@code null}.
     *
     * @param failsOnNull {@code true} to throw an exception on null values; {@code false} to
     *                    substitute the literal {@code "null"} text instead
     */
    public static void setInterpolateFailsOnNull(boolean failsOnNull) {
        interpolateFailsOnNull = failsOnNull;
    }

    /**
     * Replaces each placeholder token of {@code "{}"} in the template with the next supplied argument.
     * If a supplied argument is {@code null} and fail-on-null mode is enabled, an
     * {@link IllegalArgumentException} is thrown. If no arguments are provided, the original string is
     * returned unchanged.
     *
     * @param s    template string containing {@code "{}"} placeholders
     * @param args values to insert into the template
     * @return the interpolated string
     * @throws IllegalArgumentException if a supplied argument is {@code null} and fail-on-null mode is enabled
     * @see #setInterpolateFailsOnNull(boolean)
     */
    public static @NotNull String interpolate(@NotNull String s, Object @NotNull ... args) {
        if (s.isBlank() || args.length == 0) return s;

        StringBuilder sb = new StringBuilder();
        int lastIndex = 0;
        int argIndex = 0;

        while (argIndex < args.length) {
            int index = s.indexOf("{}", lastIndex);

            if (index == -1) break;

            sb.append(s, lastIndex, index);

            Object arg = args[argIndex++];

            if (arg == null && interpolateFailsOnNull) {
                throw new IllegalArgumentException("Interpolation failed due to null argument");
            }

            sb.append(arg == null ? "null" : arg.toString());

            lastIndex = index + 2;
        }

        sb.append(s.substring(lastIndex));

        return sb.toString();
    }

    /**
     * Convenience overload for interpolating a single value into a template.
     *
     * @param s   template string containing {@code "{}"} placeholders
     * @param arg value to insert into the template
     * @return the interpolated string
     * @see #interpolate(String, Object...)
     */
    public static @NotNull String interpolate(@NotNull String s, Object arg) {
        return interpolate(s, new Object[]{arg});
    }

    /**
     * Convenience overload for interpolating two values into a template.
     *
     * @param s    template string containing {@code "{}"} placeholders
     * @param arg  first value to insert into the template
     * @param arg2 second value to insert into the template
     * @return the interpolated string
     * @see #interpolate(String, Object...)
     */
    public static @NotNull String interpolate(@NotNull String s, Object arg, Object arg2) {
        return interpolate(s, new Object[]{arg, arg2});
    }

    public static @NotNull String interpolateAll(@NotNull String s, Object arg) {
        if (s.isBlank()) return s;

        if (interpolateFailsOnNull && arg == null) {
            throw new IllegalArgumentException("Interpolation failed due to null argument");

        } else {
            String replacement = (arg == null) ? "null" : arg.toString();

            return s.replace("{}", replacement);
        }
    }
}
