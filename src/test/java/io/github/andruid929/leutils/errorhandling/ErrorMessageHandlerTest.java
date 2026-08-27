package io.github.andruid929.leutils.errorhandling;

import static org.junit.jupiter.api.Assertions.*;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ErrorMessageHandlerTest {

    @Test
    @DisplayName("Get simple error message from exception")
    void simpleErrorMessageTest() {
        String errorMessage = ErrorMessageHandler.simpleErrorMessage(new NullPointerException());

        assertTrue(errorMessage.endsWith("NullPointerException"));
    }

    @Test
    @DisplayName("Get stack trace without message")
    void getStackTraceTest() {
        Exception e = new RuntimeException("Test exception");
        String stackTrace = ErrorMessageHandler.getStackTrace(e);

        // Verify it contains the current class/method name as it should be in the stack trace
        assertTrue(stackTrace.contains("ErrorMessageHandlerTest.getStackTraceTest"));
        // By default, it should NOT include the exception message at the top
        assertFalse(stackTrace.startsWith("java.lang.RuntimeException: Test exception"));
    }

    @Test
    @DisplayName("Get stack trace with exception message included")
    void getStackTraceWithIncludeMessageTest() {
        Exception e = new RuntimeException("Test exception");

        String stackTrace = ErrorMessageHandler.getStackTrace(e, true);

        // Should start with the simple error message
        assertTrue(stackTrace.startsWith("java.lang.RuntimeException: Test exception"));
        // Should also contain the stack trace elements
        assertTrue(stackTrace.contains("ErrorMessageHandlerTest.getStackTraceWithIncludeMessageTest"));
    }

    @Test
    @DisplayName("Get stack trace from throwable with message")
    void getStackTraceWithThrowableTest() {
        Throwable t = new Throwable("Test throwable");
        String stackTrace = ErrorMessageHandler.getStackTrace(t, true);

        // For non-Exception Throwable, it should now correctly include the message
        assertTrue(stackTrace.startsWith("java.lang.Throwable: Test throwable"));
        assertTrue(stackTrace.contains("ErrorMessageHandlerTest.getStackTraceWithThrowableTest"));
    }

    @Test
    @DisplayName("Get simple error message from exception with message")
    void simpleErrorMessageTest1() {
        String errorMessage = ErrorMessageHandler.simpleErrorMessage(new NullPointerException("Value is null"));

        assertTrue(errorMessage.endsWith("null"));
    }

    @Test
    @DisplayName("Trace root cause of wrapped exceptions")
    void throwableRootTracer() {
        Error e = throwsError();

        String rootMessage = ErrorMessageHandler.throwableRootMessageTracer(e);
        Throwable throwableRoot = ErrorMessageHandler.throwableRootTracer(e);

        String message = e.getMessage();

        assertEquals(NumberFormatException.class, throwableRoot.getClass());
        assertEquals(Error.class, e.getClass());

        assertTrue(rootMessage.startsWith("For input"));
        assertTrue(message.contains("IllegalStateException"));
    }

    @Test
    @DisplayName("Root tracer returns same exception when no cause")
    void rootTracerWithNoCause() {
        Exception simple = new Exception("Simple error");
        assertEquals(simple, ErrorMessageHandler.throwableRootTracer(simple));
    }

    @Test
    @DisplayName("Root message tracer returns empty string for null message")
    void rootMessageTracerWithNullMessage() {
        Exception rootWithoutMessage = new Exception();
        Exception wrapped = new Exception(rootWithoutMessage);

        assertEquals("", ErrorMessageHandler.throwableRootMessageTracer(wrapped));
    }

    @Test
    @DisplayName("Trace root cause through multiple exception levels")
    void rootTracerMultipleLevels() {
        NumberFormatException nfe = new NumberFormatException("NFE");
        IllegalStateException ise = new IllegalStateException(nfe);
        RuntimeException rte = new RuntimeException(ise);

        assertEquals(nfe, ErrorMessageHandler.throwableRootTracer(rte));
        assertEquals("NFE", ErrorMessageHandler.throwableRootMessageTracer(rte));
    }

    @Contract(" -> new")
    private @NotNull Error throwsError() throws Error {
        var nfe = new NumberFormatException("For input String \"number\"");

        var ise = new IllegalStateException(nfe);

        var iae = new IllegalArgumentException(ise);

        var re = new UnsupportedOperationException(iae);

        return new Error(re);
    }


}
