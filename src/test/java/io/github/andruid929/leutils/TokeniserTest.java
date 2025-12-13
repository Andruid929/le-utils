package io.github.andruid929.leutils;

import io.github.andruid929.leutils.tokeniser.Tokeniser;
import io.github.andruid929.leutils.tokeniser.UnclosedQuoteException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokeniserTest {

    private final Tokeniser token = Tokeniser.tokenise("--d hello world \"Write Once Run Anywhere\"");

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
    void unclosedExceptionTest() {
        assertThrows(UnclosedQuoteException.class, () -> Tokeniser.tokenise("Java: \"Write once Run Anywhere"));
    }

    @Test
    void toStringTest() {
        String toStringS = token.toString();

        assertTrue(toStringS.startsWith("Token") && toStringS.endsWith("}"));
    }
}