package io.github.andruid929.leutils.errorhandling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorMessageHandlerTest {

    @Test
    void simpleErrorMessageTest() {
        String errorMessage = ErrorMessageHandler.simpleErrorMessage(new NullPointerException());

        assertTrue(errorMessage.endsWith("NullPointerException"));
    }

    @Test
    void simpleErrorMessageTest1() {
        String errorMessage = ErrorMessageHandler.simpleErrorMessage(new NullPointerException("Value is null"));

        assertTrue(errorMessage.endsWith("null"));
    }

}