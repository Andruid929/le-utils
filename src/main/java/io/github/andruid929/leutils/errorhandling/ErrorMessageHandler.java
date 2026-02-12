package io.github.andruid929.leutils.errorhandling;

import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Simple utility class for formatting and printing error messages
 *
 * @author Andrew Jones
 * @since 1.2
 */

public final class ErrorMessageHandler {

    /**
     * Utility classes cannot be instantiated
     */

    private ErrorMessageHandler() {
    }

    /**
     * Get the specified exception's class name and the error message if any.
     * <p>
     * For example, say an {@link IllegalArgumentException} is thrown with message {@code expected int here}<br>
     * this method will return {@code java.lang.IllegalArgumentException: expected int here}.<br>
     * For exceptions thrown without an error message, just the exception class is returned.
     *
     * @param e the expected exception.
     * @return the exception class name and error message if any is present.
     */

    public static @NotNull String simpleErrorMessage(@NotNull Throwable e) {
        String exceptionName = e.getClass().getName();

        String errorMessage = e.getMessage();

        if (errorMessage == null || errorMessage.isBlank()) {
            return exceptionName;
        }

        return exceptionName.concat(": ").concat(errorMessage);
    }

    /**
     * Retrieves the stack trace of the specified {@code Throwable} as a formatted string.
     * Optionally, the exception's class name and error message can be included at the top
     * of the stack trace.
     *
     * @param e the exception or throwable whose stack trace is to be retrieved.
     * @param includeExceptionMessage if {@code true}, the exception's class name and
     *                                error message are included at the top of the stack trace.
     *                                If {@code false} or not specified, only the stack trace
     *                                is returned.
     * @return the formatted stack trace as a string. If {@code includeExceptionMessage} is
     *         {@code true}, the exception class name and message are prefixed to the stack trace.
     */
    public static String getStackTrace(@NotNull Throwable e, boolean @NotNull ... includeExceptionMessage) {
        Stream<String> stream = Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString);

        String lineSeparator = System.lineSeparator();

        String stackTrace = stream.collect(Collectors.joining(lineSeparator));

        if (includeExceptionMessage.length != 0 && includeExceptionMessage[0]) {
            String errorMessage = simpleErrorMessage(e).concat(System.lineSeparator());

            return errorMessage.concat(stackTrace);
        }

        return stackTrace;
    }

    /**
     * Print the value of {@link #simpleErrorMessage(Throwable)} to {@link System#err}.<br>
     * This method is effectively the same as calling
     * {@link #printSimpleErrorMessage(Throwable, PrintStream) printSimpleErrorMessage(e, System.err)}
     *
     * @param e the expected exception.
     */

    public static void printSimpleErrorMessage(@NotNull Throwable e) {
        System.err.println(simpleErrorMessage(e));
    }

    /**
     * Print the value of {@link #simpleErrorMessage(Throwable)} to a
     * specific print stream given by the {@code printStream} parameter.
     *
     * @param e           the expected exception
     * @param printStream the stream to print the error message to.
     */

    public static void printSimpleErrorMessage(@NotNull Throwable e, @NotNull PrintStream printStream) {
        printStream.println(simpleErrorMessage(e));
    }
}
