package io.github.andruid929.leutils.tokeniser;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

class TokenTest {

    private final Token token = Token.tokenise("--d \"src\\main\\resources\" world \"Write Once Run Anywhere\" -t");

    private final Token dashyToken = Token.tokenise("--d:s -fad -d --s -a -t");

    @Test
    @DisplayName("Get arguments from tokenized string")
    void getArgumentsTest() {
        assertEquals("world", token.getArguments().get(2));
    }

    @Test
    @DisplayName("Get number of arguments")
    void getNumberOfArgumentsTest() {
        assertEquals(5, token.getNumberOfArguments());
    }

    @Test
    @DisplayName("Check if token has no arguments")
    void hasNoArgumentsTest() {
        assertFalse(token.hasNoArguments());
    }

    @Test
    @DisplayName("Extract path from argument")
    void getPathTest() {
        assertEquals(Path.of("src\\main\\resources"), token.getPathFromArgument(1));
    }

    @Test
    @DisplayName("Check if token has specified number of arguments")
    void hasArgumentsTest() {
        assertTrue(token.hasArguments(5));
    }

    @Test
    @DisplayName("Get argument at index")
    void getArgumentTest() {
        assertEquals("world", token.getArgument(2));
    }

    @Test
    @DisplayName("Get first argument")
    void getFirstArgumentTest() {
        assertEquals("--d", token.getFirstArgument());
    }

    @Test
    @DisplayName("Get last argument")
    void getLastArgumentTest() {
        assertEquals("-t", token.getLastArgument());
    }

    @Test
    @DisplayName("Throw exception for unclosed quote")
    void unclosedExceptionTest() {
        assertThrows(UnclosedQuoteException.class, () -> Token.tokenise("Java: \"Write once Run Anywhere"));
    }

    @Test
    @DisplayName("Get flags from token")
    void getFlags() {
        assertEquals(4, dashyToken.getFlags().size());
    }

    @Test
    @DisplayName("Get options from token")
    void getOptions() {
        assertEquals(2, dashyToken.getOptions().size());
    }


    @Test
    @DisplayName("Handle escaped arguments")
    void escapedArguments() {
        Token escapeSeq = Token.tokenise("hey \\\"world\\\"");

        assertEquals("\"world\"", escapeSeq.getArgument(1));
    }

    @Test
    @DisplayName("Get flag at index")
    void getFlag() {
        assertEquals("-fad", dashyToken.getFlag(0, true));
        assertEquals("fad", dashyToken.getFlag(0, false));
    }

    @Test
    @DisplayName("Get flag value")
    void getFlagValue() {
        assertEquals('d', dashyToken.getFlagValue(1));
    }

    @Test
    @DisplayName("Single dash is not recognized as flag")
    void singleDashIsNotAFlag() {
        Token loneDash = Token.tokenise("-");

        assertEquals(1, loneDash.getNumberOfArguments());
        assertEquals(0, loneDash.getFlags().size());
    }

    @Test
    @DisplayName("Get option at index")
    void getOption() {
        assertEquals("d:s", dashyToken.getOption(0, false));
        assertEquals("--d:s", dashyToken.getOption(0, true));
    }

    @Test
    @DisplayName("Handle empty input")
    void emptyInputTest() {
        Token empty = Token.tokenise("");
        assertTrue(empty.hasNoArguments());
        assertEquals(0, empty.getNumberOfArguments());
    }

    @Test
    @DisplayName("Handle trailing escape character")
    void trailingEscapeTest() {
        Token trailing = Token.tokenise("endsInBackslash\\");
        assertEquals("endsInBackslash\\", trailing.getArgument(0));
    }

    @Test
    @DisplayName("Test token equality and hash code")
    void equalityTest() {
        Token token1 = Token.tokenise("hello world");
        Token token2 = Token.tokenise("hello world");
        Token token3 = Token.tokenise("different");

        assertEquals(token1, token2);
        assertEquals(token1.hashCode(), token2.hashCode());
        assertNotEquals(token1, token3);
    }

    @Test
    @DisplayName("Get string representation of token")
    void toStringTest() {
        String toStringS = token.toString();

        assertTrue(toStringS.startsWith("Token") && toStringS.endsWith("]"));
    }
}

