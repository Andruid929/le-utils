package io.github.andruid929.leutils.stringutil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.SAME_THREAD)
class StringFormatterTest {

    @Test
    @DisplayName("Trim basic characters from string")
    void trimCharactersBasic() {
        String input = "&Le-utils;";

        assertEquals("Le-utils", StringFormatter.trimCharacters(input));
        assertEquals("Le-utils", StringFormatter.trimCharacters(input, '&', ';'));
        assertEquals("&Le-utils;", StringFormatter.trimCharacters(input, '&', ']')); // no match
        assertEquals("&Le-utils;", StringFormatter.trimCharacters(input, '{', '}')); // no match
    }

    @Test
    @DisplayName("Trim characters with whitespace normalization")
    void trimCharactersWhitespaceNormalisation() {
        String input = "   [abc]   ";

        // Works on trimmed view
        assertEquals("abc", StringFormatter.trimCharacters(input, "[", "]"));

        // Char-char variant
        assertEquals("abc", StringFormatter.trimCharacters(input, '[', ']'));

        // No-arg variant removes first and last of trimmed => "[abc]" -> "abc"
        assertEquals("abc", StringFormatter.trimCharacters(input));
    }

    @Test
    @DisplayName("Trim single character and empty strings")
    void trimCharactersSingleCharAndEmpty() {
        assertEquals("", StringFormatter.trimCharacters("  "));     // blank stays blank
        assertEquals("a", StringFormatter.trimCharacters(" a "));   // single char unchanged after normalisation
        assertEquals("", StringFormatter.trimCharacters("", '[', ']')); // empty stays empty
        assertEquals("", StringFormatter.trimCharacters("   ", '[', ']')); // blank stays blank
    }

    @Test
    @DisplayName("Protect against over-trimming")
    void trimCharactersOverTrimProtection() {
        String input = "  abcd  ";

        // charsToTrim 0 -> no-op beyond trim
        assertEquals("abcd", StringFormatter.trimCharacters(input, 0));

        // charsToTrim too large -> returns normalised
        assertEquals("abcd", StringFormatter.trimCharacters(input, 3));

        // valid symmetric trim
        assertEquals("bc", StringFormatter.trimCharacters(input, 1));
    }

    @Test
    @DisplayName("Trim characters with different start and end patterns")
    void trimCharactersStartEndVariants() {
        String input = "([{Le-utils}])";

        assertEquals("[{Le-utils}]", StringFormatter.trimCharacters(input)); // default
        assertEquals("Le-utils}", StringFormatter.trimCharacters(input, "([{", "])"));
        assertEquals("Le-util", StringFormatter.trimCharacters(input, "([{", "s}])"));

        // empty end => only prefix considered; endsWith("") is true, so remove prefix only
        assertEquals("Le-utils}])", StringFormatter.trimCharacters(input, "([{", ""));

        // empty start => only suffix considered; startsWith("") is true, so remove suffix only
        assertEquals("([{Le-utils", StringFormatter.trimCharacters(input, "", "}])"));

        // both empty => no-op beyond trim
        assertEquals(input.trim(), StringFormatter.trimCharacters(input, "", ""));

        // mismatch => returns trimmed input
        assertEquals(input.trim(), StringFormatter.trimCharacters(input, "#", "]"));
    }

    @Test
    @DisplayName("Interpolate values into format string")
    void interpolateTest() {
        String input = "Hi, I am {} and it is {}℃ out";

        assertEquals("Hi, I am Andrew and it is 25℃ out", StringFormatter.interpolate(input, "Andrew", 25));
        assertEquals("Hi, I am Andrew and it is 25℃ out", StringFormatter.interpolate(input, "Andrew", 25, "extra arg"));
        assertEquals("Hi, I am Andrew and it is {}℃ out", StringFormatter.interpolate(input, "Andrew"));
        assertEquals("Hi, I am {} and it is {}℃ out", StringFormatter.interpolate(input));

        String invalidCurlyBraceFormatString = "Hi, I am { } and it is {}℃ out";

        assertEquals("Hi, I am { } and it is 25℃ out", StringFormatter.interpolate(invalidCurlyBraceFormatString, 25));

        assertThrows(IllegalArgumentException.class, () -> StringFormatter.interpolate(input, null, "25"));

        try {
            StringFormatter.setInterpolateFailsOnNull(false);

            assertEquals("Hi, I am null and it is 25℃ out", StringFormatter.interpolate(input, null, 25));
            // Additional test case: mixed object types
            assertEquals("Mixed: 1, 2.0, string", StringFormatter.interpolate("Mixed: {}, {}, {}", 1, 2.0, "string"));

        } finally {
            StringFormatter.setInterpolateFailsOnNull(true);
        }
    }

    @Test
    @DisplayName("Interpolate all placeholders with same value")
    void interpolateAllTest() {
        String value = "The {} is hot, so is the {} as the {} is {}y";

        assertEquals("The roof is hot, so is the roof as the roof is roofy", StringFormatter.interpolateAll(value, "roof"));
    }
}
