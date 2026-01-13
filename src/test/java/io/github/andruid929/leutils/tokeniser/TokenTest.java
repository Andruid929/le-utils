package io.github.andruid929.leutils.tokeniser;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {

    private final Token token = Token.tokenise("--d \"src\\main\\resources\" world \"Write Once Run Anywhere\" -t");

    private final Token dashyToken = Token.tokenise("--d:s -fad -d --s -a -t");

    @Test
    void getArgumentsTest() {
        assertEquals("world", token.getArguments().get(2));
    }

    @Test
    void getNumberOfArgumentsTest() {
        assertEquals(5, token.getNumberOfArguments());
    }

    @Test
    void hasNoArgumentsTest() {
        assertFalse(token.hasNoArguments());
    }

    @Test
    void getPathTest() {
        assertEquals(Path.of("src\\main\\resources"), token.getPathFromArgument(1));
    }

    @Test
    void hasArgumentsTest() {
        assertTrue(token.hasArguments(5));
    }

    @Test
    void getArgumentTest() {
        assertEquals("world", token.getArgument(2));
    }

    @Test
    void getFirstArgumentTest() {
        assertEquals("--d", token.getFirstArgument());
    }

    @Test
    void getLastArgumentTest() {
        assertEquals("-t", token.getLastArgument());
    }

    @Test
    void unclosedExceptionTest() {
        assertThrows(UnclosedQuoteException.class, () -> Token.tokenise("Java: \"Write once Run Anywhere"));
    }

    @Test
    void getFlags() {
        assertEquals(4, dashyToken.getFlags().size());
    }

    @Test
    void getOptions() {
        assertEquals(2, dashyToken.getOptions().size());
    }


    @Test
    void escapedArguments() {
        Token escapeSeq = Token.tokenise("hey \\\"world\\\"");

        assertEquals("\"world\"", escapeSeq.getArgument(1));
    }

    @Test
    void getFlag() {
        assertEquals("-fad", dashyToken.getFlag(0, true));
        assertEquals("fad", dashyToken.getFlag(0, false));
    }

    @Test
    void getFlagValue() {
        assertEquals('d', dashyToken.getFlagValue(1));
    }

    @Test
    void singleDashIsNotAFlag() {
        Token loneDash = Token.tokenise("-");

        assertEquals(1, loneDash.getNumberOfArguments());
        assertEquals(0, loneDash.getFlags().size());
    }

    @Test
    void getOption() {
        assertEquals("d:s", dashyToken.getOption(0, false));
        assertEquals("--d:s", dashyToken.getOption(0, true));
    }

    @Test
    void emptyInputTest() {
        Token empty = Token.tokenise("");
        assertTrue(empty.hasNoArguments());
        assertEquals(0, empty.getNumberOfArguments());
    }

    @Test
    void trailingEscapeTest() {
        Token trailing = Token.tokenise("endsInBackslash\\");
        assertEquals("endsInBackslash\\", trailing.getArgument(0));
    }

    @Test
    void equalityTest() {
        Token token1 = Token.tokenise("hello world");
        Token token2 = Token.tokenise("hello world");
        Token token3 = Token.tokenise("different");

        assertEquals(token1, token2);
        assertEquals(token1.hashCode(), token2.hashCode());
        assertNotEquals(token1, token3);
    }

    @Test
    void toStringTest() {
        String toStringS = token.toString();

        assertTrue(toStringS.startsWith("Token") && toStringS.endsWith("]"));
    }
}