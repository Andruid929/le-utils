package io.github.andruid929.leutils.stringutil;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import io.github.andruid929.leutils.stringutil.Separators;

class SeparatorsTest {

    @Test
    void separateAsListAndSetWithRegexDelimiter() {
        String input = "io.github.andruid929io:le-utils:le-utils:3.2.0";

        List<String> separatedList = Separators.separateAsList(input, ":");

        Set<String> separatedSet = Separators.separateAsSet(input, ":");

        assertEquals(4, separatedList.size());
        assertEquals(3, separatedSet.size());

        assertTrue(separatedList.contains("le-utils"));
        assertTrue(separatedSet.contains("3.2.0"));
    }

    @Test
    void separateLiteralDelimiterDoesNotTreatRegex() {
        String input = "a.b.c";

        // regex split on "." splits every char
        List<String> regexSplit = Separators.separateAsList(input, "\\.");
        assertEquals(List.of("a", "b", "c"), regexSplit);

        // literal split on "." without escaping should keep dots; with literalSeparateAsList it splits correctly
        List<String> literalSplit = Separators.literalSeparateAsList(input, ".");
        assertEquals(List.of("a", "b", "c"), literalSplit);

        Set<String> literalSet = Separators.literalSeparateAsSet("x|y|x", "|");
        assertEquals(Set.of("x", "y"), literalSet);
    }

}
