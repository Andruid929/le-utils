package io.github.andruid929.leutils.strings;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import io.github.andruid929.leutils.stringutil.Separators;
import io.github.andruid929.leutils.stringutil.StringFormatter;
import io.github.andruid929.leutils.stringutil.StringNormaliser;

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
 * @deprecated This class has been deprecated since 4.4.0. Use {@link StringFormatter}, {@link Separators}
 * and {@link StringNormaliser} instead.
 */

@Deprecated
public final class StringUtil {

    private StringUtil() {
    }

    /**
     * Removes the first and last characters from the input, after trimming surrounding whitespace.
     * Returns the trimmed input unchanged if its length is less than 2.
     *
     * @param input non-null input string
     * @return trimmed input without the first and last characters, or the trimmed input if not applicable
     * @deprecated Use {@link StringFormatter#trimCharacters(String)} instead.
     * Scheduled for removal in version 5.0.0.
     */

    @Deprecated
    public static @NotNull String trimCharacters(@NotNull String input) {
        return StringFormatter.trimCharacters(input);
    }

    /**
     * Removes {@code charsToTrim} characters from both the start and the end of the input,
     * after trimming surrounding whitespace.
     * If {@code charsToTrim <= 0} or the operation would over-trim, returns the trimmed input unchanged.
     *
     * @param input       non-null input string
     * @param charsToTrim number of characters to remove from both ends
     * @return the trimmed-and-sliced substring, or the trimmed input if not applicable
     * @deprecated Use {@link StringFormatter#trimCharacters(String, int)} instead.
     * Scheduled for removal in version 5.0.0.
     */

    @Deprecated
    public static @NotNull String trimCharacters(@NotNull String input, int charsToTrim) {
        return StringFormatter.trimCharacters(input, charsToTrim);
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
     * @deprecated Use {@link StringFormatter#trimCharacters(String, String, String)} instead.
     * Scheduled for removal in version 5.0.0.
     */

    @Deprecated
    public static @NotNull String trimCharacters(@NotNull String input, @NotNull String startChars, @NotNull String endChars) {
        return StringFormatter.trimCharacters(input, startChars, endChars);
    }

    /**
     * Conditionally removes the first and last characters if they match {@code firstChar} and {@code lastChar},
     * after trimming surrounding whitespace. Otherwise, returns the trimmed input.
     *
     * @param input     non-null input string
     * @param firstChar expected first character
     * @param lastChar  expected last character
     * @return the trimmed-and-sliced substring or the trimmed input if not applicable
     * @deprecated Use {@link StringFormatter#trimCharacters(String, char, char)} instead.
     * Scheduled for removal in version 5.0.0.
     */
    @Deprecated
    public static @NotNull String trimCharacters(@NotNull String input, char firstChar, char lastChar) {
        return StringFormatter.trimCharacters(input, firstChar, lastChar);
    }

    /**
     * Splits the input by a regex delimiter and returns an unmodifiable list.
     * For literal delimiters, prefer {@link #literalSeparateAsList(String, String)}.
     *
     * @param input          non-null input string
     * @param regexDelimiter a regex delimiter used by String#split
     * @return unmodifiable list of parts (possibly empty)
     * @deprecated Use {@link Separators#separateAsList(String, String)} instead.
     * Scheduled for removal in version 5.0.0.
     */

    @Deprecated
    public static @NotNull @Unmodifiable List<String> separateAsList(@NotNull String input, String regexDelimiter) {
        return Separators.separateAsList(input, regexDelimiter);
    }

    /**
     * Splits the input by a literal delimiter (no regex semantics) and returns an unmodifiable list.
     *
     * @param input            non-null input string
     * @param literalDelimiter literal delimiter to split on
     * @return unmodifiable list of parts (possibly empty)
     * @deprecated Use {@link Separators#literalSeparateAsList(String, String)} instead.
     * Scheduled for removal in version 5.0.0.
     */

    @Deprecated
    public static @NotNull @Unmodifiable List<String> literalSeparateAsList(@NotNull String input, String literalDelimiter) {
        return Separators.literalSeparateAsList(input, literalDelimiter);
    }

    /**
     * Splits the input by a regex delimiter and returns an unmodifiable set of unique parts.
     * For literal delimiters, prefer {@link #literalSeparateAsSet(String, String)}.
     *
     * @param input          non-null input string
     * @param regexDelimiter a regex delimiter used by String#split
     * @return unmodifiable set of parts (possibly empty)
     * @deprecated Use {@link Separators#separateAsSet(String, String)} instead.
     * Scheduled for removal in version 5.0.0.
     */

    @Deprecated
    public static @NotNull @Unmodifiable Set<String> separateAsSet(@NotNull String input, String regexDelimiter) {
        return Separators.separateAsSet(input, regexDelimiter);
    }

    /**
     * Splits the input by a literal delimiter (no regex semantics) and returns an unmodifiable set.
     *
     * @param input            non-null input string
     * @param literalDelimiter literal delimiter to split on
     * @return unmodifiable set of parts (possibly empty)
     * @deprecated Use {@link Separators#literalSeparateAsSet(String, String)} instead.
     * Scheduled for removal in version 5.0.0.
     */

    @Deprecated
    public static @NotNull @Unmodifiable Set<String> literalSeparateAsSet(@NotNull String input, String literalDelimiter) {
        return Separators.literalSeparateAsSet(input, literalDelimiter);
    }

    /**
     * Returns the last valid character index, or -1 for an empty string.
     *
     * @param input non-null input string
     * @return input.length() - 1, or -1 if input is empty
     * @deprecated This method provides minimal value; use {@code input.length() - 1} instead.
     * Scheduled for removal in version 5.0.0.
     */

    @Deprecated
    @Contract(pure = true)
    public static int getLastCharIndex(@NotNull String input) {
        return input.length() - 1;
    }

    /**
     * Normalises a URL by trimming whitespace, replacing spaces according to the specified mode,
     * and converting backslashes to forward slashes.
     *
     * @param input non-null URL string to normalise
     * @param mode  space replacement strategy (e.g. %20 or hyphen)
     * @return normalised URL string
     * @deprecated Use {@link StringNormaliser#normaliseUrl(String, StringNormaliser.SpaceMode)} instead.
     * Scheduled for removal in version 5.0.0.
     */
    @Deprecated
    public static @NotNull String normaliseUrl(@NotNull String input, @NotNull SpaceMode mode) {
        String trimmedUrl = input.trim();

        return trimmedUrl.replaceAll(" ", mode.literalReplacement)
                .replaceAll(Pattern.quote("\\"), "/");
    }

    /**
     * Defines how spaces should be replaced in URL normalisation.
     *
     * @deprecated Use {@link StringNormaliser.SpaceMode} instead.
     * Scheduled for removal in version 5.0.0.
     */
    @Deprecated
    public enum SpaceMode {

        /**
         * Replace spaces with an encoded space (%20).
         */
        SPACE("%20"),

        /**
         * Replace spaces with hyphens (-).
         */
        HYPHEN("-");

        /**
         * Replacement text used for spaces in the selected mode.
         */
        public final String literalReplacement;

        SpaceMode(String literalReplacement) {
            this.literalReplacement = literalReplacement;
        }

    }
}
