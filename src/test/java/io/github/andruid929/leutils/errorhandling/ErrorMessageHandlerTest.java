package io.github.andruid929.leutils.errorhandling;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

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

    @Test
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
    void rootTracerWithNoCause() {
        Exception simple = new Exception("Simple error");
        assertEquals(simple, ErrorMessageHandler.throwableRootTracer(simple));
    }

    @Test
    void rootMessageTracerWithNullMessage() {
        Exception rootWithoutMessage = new Exception();
        Exception wrapped = new Exception(rootWithoutMessage);

        assertEquals("", ErrorMessageHandler.throwableRootMessageTracer(wrapped));
    }

    @Test
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