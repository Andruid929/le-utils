package io.github.andruid929.leutils.errorhandling;

import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;

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

    public static @NotNull String simpleErrorMessage(@NotNull Exception e) {
        String exceptionName = e.getClass().getName();

        String errorMessage = e.getMessage();

        if (errorMessage == null || errorMessage.isBlank()) {
            return exceptionName;
        }

        return exceptionName.concat(": ").concat(errorMessage);
    }

    /**
     * Print the value of {@link #simpleErrorMessage(Exception)} to {@link System#err}.<br>
     * This method is effectively the same as calling
     * {@link #printSimpleErrorMessage(Exception, PrintStream) printSimpleErrorMessage(e, System.err)}
     *
     * @param e the expected exception.
     */

    public static void printSimpleErrorMessage(@NotNull Exception e) {
        System.err.println(simpleErrorMessage(e));
    }

    /**
     * Print the value of {@link #simpleErrorMessage(Exception)} to a
     * specific print stream given by the {@code printStream} parameter.
     *
     * @param e           the expected exception
     * @param printStream the stream to print the error message to.
     */

    public static void printSimpleErrorMessage(@NotNull Exception e, @NotNull PrintStream printStream) {
        printStream.println(simpleErrorMessage(e));
    }
}
