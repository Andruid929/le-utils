package io.github.andruid929.leutils;

import io.github.andruid929.leutils.tokeniser.Token;
import io.github.andruid929.leutils.tokeniser.UnclosedQuoteException;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {

    private final Token token = Token.tokenise("--d \"src\\main\\resources\" world \"Write Once Run Anywhere\"");

    @Test
    void getArgumentsTest() {
        assertEquals("world", token.getArguments().get(2));
    }

    @Test
    void getNumberOfArgumentsTest() {
        assertEquals(4, token.getNumberOfArguments());
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
        assertTrue(token.hasArguments(4));
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
        assertEquals("Write Once Run Anywhere", token.getLastArgument());
    }

    @Test
    void unclosedExceptionTest() {
        assertThrows(UnclosedQuoteException.class, () -> Token.tokenise("Java: \"Write once Run Anywhere"));
    }

    @Test
    void toStringTest() {
        String toStringS = token.toString();

        assertTrue(toStringS.startsWith("Token") && toStringS.endsWith("}"));
    }
}