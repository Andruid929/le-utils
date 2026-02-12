package io.github.andruid929.leutils.errorhandling;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class ErrorMessageHandlerTest {

    @Test
    void simpleErrorMessageTest() {
        String errorMessage = ErrorMessageHandler.simpleErrorMessage(new NullPointerException());

        assertTrue(errorMessage.endsWith("NullPointerException"));
    }

    @Test
    void getStackTraceTest() {
        Exception e = new RuntimeException("Test exception");
        String stackTrace = ErrorMessageHandler.getStackTrace(e);

        // Verify it contains the current class/method name as it should be in the stack trace
        assertTrue(stackTrace.contains("ErrorMessageHandlerTest.getStackTraceTest"));
        // By default, it should NOT include the exception message at the top
        assertFalse(stackTrace.startsWith("java.lang.RuntimeException: Test exception"));
    }

    @Test
    void getStackTraceWithIncludeMessageTest() {
        Exception e = new RuntimeException("Test exception");

        String stackTrace = ErrorMessageHandler.getStackTrace(e, true);

        // Should start with the simple error message
        assertTrue(stackTrace.startsWith("java.lang.RuntimeException: Test exception"));
        // Should also contain the stack trace elements
        assertTrue(stackTrace.contains("ErrorMessageHandlerTest.getStackTraceWithIncludeMessageTest"));
    }

    @Test
    void getStackTraceWithThrowableTest() {
        Throwable t = new Throwable("Test throwable");
        String stackTrace = ErrorMessageHandler.getStackTrace(t, true);

        // For non-Exception Throwable, it should now correctly include the message
        assertTrue(stackTrace.startsWith("java.lang.Throwable: Test throwable"));
        assertTrue(stackTrace.contains("ErrorMessageHandlerTest.getStackTraceWithThrowableTest"));
    }

    @Test
    void simpleErrorMessageTest1() {
        String errorMessage = ErrorMessageHandler.simpleErrorMessage(new NullPointerException("Value is null"));

        assertTrue(errorMessage.endsWith("null"));
    }

}