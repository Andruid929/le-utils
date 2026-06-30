package io.github.andruid929.leutils.stringutil;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class StringFormatterTest {

    @Test
    void trimCharactersBasic() {
        String input = "&Le-utils;";

        assertEquals("Le-utils", StringFormatter.trimCharacters(input));
        assertEquals("Le-utils", StringFormatter.trimCharacters(input, '&', ';'));
        assertEquals("&Le-utils;", StringFormatter.trimCharacters(input, '&', ']')); // no match
        assertEquals("&Le-utils;", StringFormatter.trimCharacters(input, '{', '}')); // no match
    }

    @Test
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
    void trimCharactersSingleCharAndEmpty() {
        assertEquals("", StringFormatter.trimCharacters("  "));     // blank stays blank
        assertEquals("a", StringFormatter.trimCharacters(" a "));   // single char unchanged after normalisation
        assertEquals("", StringFormatter.trimCharacters("", '[', ']')); // empty stays empty
        assertEquals("", StringFormatter.trimCharacters("   ", '[', ']')); // blank stays blank
    }

    @Test
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
    void interpolateTest() {
        String input = "Hi, I am {} and it is {}℃ out";

        assertEquals("Hi, I am Andrew and it is 25℃ out", StringFormatter.interpolate(input, "Andrew", 25));
    }
}
