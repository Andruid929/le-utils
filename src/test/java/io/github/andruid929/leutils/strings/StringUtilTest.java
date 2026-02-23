package io.github.andruid929.leutils.strings;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

    @Test
    void trimCharactersBasic() {
        String input = "&Le-utils;";

        assertEquals("Le-utils", StringUtil.trimCharacters(input));
        assertEquals("Le-utils", StringUtil.trimCharacters(input, '&', ';'));
        assertEquals("&Le-utils;", StringUtil.trimCharacters(input, '&', ']')); // no match
        assertEquals("&Le-utils;", StringUtil.trimCharacters(input, '{', '}')); // no match
    }

    @Test
    void trimCharactersWhitespaceNormalisation() {
        String input = "   [abc]   ";

        // Works on trimmed view
        assertEquals("abc", StringUtil.trimCharacters(input, "[", "]"));

        // Char-char variant
        assertEquals("abc", StringUtil.trimCharacters(input, '[', ']'));

        // No-arg variant removes first and last of trimmed => "[abc]" -> "abc"
        assertEquals("abc", StringUtil.trimCharacters(input));
    }

    @Test
    void trimCharactersSingleCharAndEmpty() {
        assertEquals("", StringUtil.trimCharacters("  "));     // blank stays blank
        assertEquals("a", StringUtil.trimCharacters(" a "));   // single char unchanged after normalisation
        assertEquals("", StringUtil.trimCharacters("", '[', ']')); // empty stays empty
        assertEquals("", StringUtil.trimCharacters("   ", '[', ']')); // blank stays blank
    }

    @Test
    void trimCharactersOverTrimProtection() {
        String input = "  abcd  ";

        // charsToTrim 0 -> no-op beyond trim
        assertEquals("abcd", StringUtil.trimCharacters(input, 0));

        // charsToTrim too large -> returns normalised
        assertEquals("abcd", StringUtil.trimCharacters(input, 3));

        // valid symmetric trim
        assertEquals("bc", StringUtil.trimCharacters(input, 1));
    }

    @Test
    void trimCharactersStartEndVariants() {
        String input = "([{Le-utils}])";

        assertEquals("[{Le-utils}]", StringUtil.trimCharacters(input)); // default
        assertEquals("Le-utils}", StringUtil.trimCharacters(input, "([{", "])"));
        assertEquals("Le-util", StringUtil.trimCharacters(input, "([{", "s}])"));

        // empty end => only prefix considered; endsWith("") is true, so remove prefix only
        assertEquals("Le-utils}])", StringUtil.trimCharacters(input, "([{", ""));

        // empty start => only suffix considered; startsWith("") is true, so remove suffix only
        assertEquals("([{Le-utils", StringUtil.trimCharacters(input, "", "}])"));

        // both empty => no-op beyond trim
        assertEquals(input.trim(), StringUtil.trimCharacters(input, "", ""));

        // mismatch => returns trimmed input
        assertEquals(input.trim(), StringUtil.trimCharacters(input, "#", "]"));
    }

    @Test
    void separateAsListAndSetWithRegexDelimiter() {
        String input = "io.github.andruid929io:le-utils:le-utils:3.2.0";

        List<String> separatedList = StringUtil.separateAsList(input, ":");

        Set<String> separatedSet = StringUtil.separateAsSet(input, ":");

        assertEquals(4, separatedList.size());
        assertEquals(3, separatedSet.size());

        assertTrue(separatedList.contains("le-utils"));
        assertTrue(separatedSet.contains("3.2.0"));
    }

    @Test
    void separateLiteralDelimiterDoesNotTreatRegex() {
        String input = "a.b.c";

        // regex split on "." splits every char
        List<String> regexSplit = StringUtil.separateAsList(input, "\\.");
        assertEquals(List.of("a", "b", "c"), regexSplit);

        // literal split on "." without escaping should keep dots; with literalSeparateAsList it splits correctly
        List<String> literalSplit = StringUtil.literalSeparateAsList(input, ".");
        assertEquals(List.of("a", "b", "c"), literalSplit);

        Set<String> literalSet = StringUtil.literalSeparateAsSet("x|y|x", "|");
        assertEquals(Set.of("x", "y"), literalSet);
    }

    @Test
    void getLastCharIndex() {
        String input = "mvn verify";

        int lastCharIndex = StringUtil.getLastCharIndex(input);

        assertEquals(9, lastCharIndex);
        assertEquals('y', input.charAt(lastCharIndex));

        assertEquals(-1, StringUtil.getLastCharIndex(""));
    }
}