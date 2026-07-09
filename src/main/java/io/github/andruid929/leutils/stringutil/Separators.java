package io.github.andruid929.leutils.stringutil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Utility class for advanced string splitting operations, supporting both regex and literal delimiters.
 *
 * @author Andrew Jones
 * @since 4.4.0
 */
public final class Separators {

    private Separators() {
    }

    /**
     * Splits the input by a regex delimiter and returns an unmodifiable list.
     * For literal delimiters, prefer {@link #literalSeparateAsList(String, String)}.
     *
     * @param input          non-null input string
     * @param regexDelimiter a regex delimiter used by String#split
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
     * @param input          non-null input string
     * @param regexDelimiter a regex delimiter used by String#split
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
}
