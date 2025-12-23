package io.github.andruid929.leutils.errorhandling;

import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;

public final class ErrorMessageHandler {

    private ErrorMessageHandler() {}

    public static @NotNull String simpleErrorMessage(@NotNull Exception e) {
        String exceptionName = e.getClass().getName();

        String errorMessage = e.getMessage();

        if (errorMessage == null || errorMessage.isBlank()) {
            return exceptionName;
        }

        return exceptionName.concat(": ").concat(errorMessage);
    }

    public static void printSimpleErrorMessage(@NotNull Exception e) {
        System.err.println(simpleErrorMessage(e));
    }

    public static void printSimpleErrorMessage(@NotNull Exception e, @NotNull PrintStream printStream) {
        printStream.println(simpleErrorMessage(e));
        printStream.close();
    }

    public static void printSimpleErrorMessage(@NotNull Exception e, @NotNull PrintStream printStream, boolean newLine) {
        if (newLine) {
            printStream.println(simpleErrorMessage(e));
        } else {
            printStream.print(simpleErrorMessage(e));
        }

        printStream.close();
    }
}
