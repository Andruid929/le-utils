package io.github.andruid929.leutils.strings;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * A utility class providing common string manipulation operations.
 *
 * <p>Notes:</p>
 * <ul>
 *   <li>All trimCharacters methods operate on a whitespace-trimmed view of the input (String#trim()).</li>
 *   <li>For empty strings, {@link #getLastCharIndex(String)} returns -1.</li>
 *   <li>Delimiter parameters in separateAsList/separateAsSet are regex by default; use the
 *       overloads with a literal delimiter to avoid regex semantics.</li>
 * </ul>
 *
 * @author Andrew Jones
 * @since 3.2.0
 */

public final class StringUtil {

    private StringUtil() {
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

        int lastCharacterIndex = getLastCharIndex(trimmedString);

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
     * @param input        non-null input string
     * @param charsToTrim  number of characters to remove from both ends
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
     * Splits the input by a regex delimiter and returns an unmodifiable list.
     * For literal delimiters, prefer {@link #literalSeparateAsList(String, String)}.
     *
     * @param input           non-null input string
     * @param regexDelimiter  a regex delimiter used by String#split
     * @return unmodifiable list of parts (possibly empty)
     */

    public static @NotNull @Unmodifiable List<String> separateAsList(@NotNull String input, String regexDelimiter) {
        String[] array = input.split(regexDelimiter);

        return List.of(array);
    }

    /**
     * Splits the input by a literal delimiter (no regex semantics) and returns an unmodifiable list.
     *
     * @param input            non-null input string
     * @param literalDelimiter literal delimiter to split on
     * @return unmodifiable list of parts (possibly empty)
     */

    public static @NotNull @Unmodifiable List<String> literalSeparateAsList(@NotNull String input, String literalDelimiter) {
        String[] array = input.split(Pattern.quote(literalDelimiter));

        return List.of(array);
    }

    /**
     * Splits the input by a regex delimiter and returns an unmodifiable set of unique parts.
     * For literal delimiters, prefer {@link #literalSeparateAsSet(String, String)}.
     *
     * @param input           non-null input string
     * @param regexDelimiter  a regex delimiter used by String#split
     * @return unmodifiable set of parts (possibly empty)
     */

    public static @NotNull @Unmodifiable Set<String> separateAsSet(@NotNull String input, String regexDelimiter) {
        List<String> separated = separateAsList(input, regexDelimiter);

        return Set.copyOf(separated);
    }

    /**
     * Splits the input by a literal delimiter (no regex semantics) and returns an unmodifiable set.
     *
     * @param input            non-null input string
     * @param literalDelimiter literal delimiter to split on
     * @return unmodifiable set of parts (possibly empty)
     */

    public static @NotNull @Unmodifiable Set<String> literalSeparateAsSet(@NotNull String input, String literalDelimiter) {
        List<String> separated = literalSeparateAsList(input, literalDelimiter);

        return Set.copyOf(separated);
    }

    /**
     * Returns the last valid character index, or -1 for an empty string.
     *
     * @param input non-null input string
     * @return input.length() - 1, or -1 if input is empty
     */

    @Contract(pure = true)
    public static int getLastCharIndex(@NotNull String input) {
        return input.length() - 1;
    }
}
