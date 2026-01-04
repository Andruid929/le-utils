package io.github.andruid929.leutils.tokeniser;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Collects space-separated arguments in a String.
 *
 * @author Andrew Jones
 * @see UnclosedQuoteException
 * @since 1.0
 */

public final class Token {

    /**
     * List of arguments in the given String.
     */

    private final List<String> arguments;

    /**
     * Argument string builder.
     */

    private final StringBuilder argumentBuilder;

    /**
     * Instantiate arguments list and builder and collect arguments from the String.
     *
     * @param input the String to collect argument from.
     */

    private Token(@NotNull String input) {
        arguments = new ArrayList<>();

        argumentBuilder = new StringBuilder();

        boolean insideQuotes = false;

        {
            for (char c : input.trim().toCharArray()) {

                if (c == '"') {
                    insideQuotes = !insideQuotes;

                    if (!insideQuotes && builderHasData()) {
                        arguments.add(argumentBuilder.toString());

                        argumentBuilder.setLength(0);
                    }

                    continue;
                }

                if (c != ' ') {
                    argumentBuilder.append(c);

                } else {

                    if (insideQuotes) {
                        argumentBuilder.append(c);
                        continue;
                    }

                    if (builderHasData()) {
                        arguments.add(argumentBuilder.toString());

                        argumentBuilder.setLength(0);
                    }
                }
            }
        }

        if (builderHasData()) { //Any arguments following a quoted argument
            arguments.add(argumentBuilder.toString());
        }

        if (insideQuotes) { //If this is true, there is an unclosed quote in the input String
            throw new UnclosedQuoteException(argumentBuilder.toString());
        }
    }

    /**
     * Get a new token instance from the specified String input.
     * <p>
     * This reads the String character by character and separates the arguments by spaces.
     * The only exception to this is if the space is inside double quotes.
     * <p>For instance, if the input String is <br>
     * {@code mkdir "location/of folder" dup existing}<br>
     * the arguments returned by {@link #getArguments()} will be<br>
     * {@code [mkdir, location/of folder, dup, existing]}.
     *
     * @param input the String to collect arguments from.
     * @return a token object.
     * @throws UnclosedQuoteException if the {@code input} has unclosed double quotes.
     */

    @Contract("_ -> new")
    public static @NotNull Token tokenise(@NotNull String input) {
        return new Token(input);
    }

    /**
     * Get the arguments present in the specified String.
     *
     * @return List of arguments as Strings.
     */

    public List<String> getArguments() {
        return arguments;
    }

    /**
     * Get flags present, if any.
     * <p>
     * Flags are arguments that start with a single dash (-).
     *
     * @return List of flags as Strings.
     * */

    public List<String> getFlags() {
        Pattern pattern = Pattern.compile("^(?<!-)-[A-Za-z]"); //Single dash followed by a letter

        return getArguments()
                .stream()
                .filter(argument -> pattern.matcher(argument).find())
                .collect(Collectors.toList());
    }

    /**
     * Get options present, if any.
     * <p>
     * Options are arguments that start with a double dash (--).
     *
     * @return List of options as Strings.
     * */

    public List<String> getOptions() {
        return getArguments()
                .stream()
                .filter(argument -> argument.startsWith("--"))
                .collect(Collectors.toList());
    }

    /**
     * Get the number of arguments found.<br>
     * This is the equivalent of calling {@code getArguments().size()}.
     *
     * @return number of arguments in the specified String.
     */

    public int getNumberOfArguments() {
        return arguments.size();
    }

    /**
     * Checks to see if there are no arguments in the specified String.<br>
     * This is the equivalent of calling {@code getArguments().isEmpty()}.
     *
     * @return {@code true} if the String contains no arguments, otherwise {@code false}.
     */

    public boolean hasNoArguments() {
        return arguments.isEmpty();
    }

    /**
     * Get the argument at the specified index.
     *
     * @param index the index to get the desired argument from.
     * @return the argument at given index if any.
     * @throws IndexOutOfBoundsException if the {@code index} doesn't exist.
     */

    public String getArgument(int index) {
        return arguments.get(index);
    }

    /**
     * Get the first argument found;
     *
     * @return the first argument, if any;
     * @throws IndexOutOfBoundsException if {@link #hasNoArguments()} returns true;
     */

    public String getFirstArgument() {
        return arguments.get(0);
    }

    /**
     * Get the last argument found;
     *
     * @return the last argument if any;
     * @throws IndexOutOfBoundsException if {@link #hasNoArguments()} returns true;
     */

    public String getLastArgument() {
        return arguments.get(arguments.size() - 1);
    }

    /**
     * Checks if the number of arguments found matches the given number.
     *
     * @param numberOfDesiredArguments the number of arguments to check against.
     * @return {@code true} if {@code numberOfDesiredArguments} matches {@link #getNumberOfArguments()}.
     */

    public boolean hasArguments(int numberOfDesiredArguments) {
        return arguments.size() == numberOfDesiredArguments;
    }

    /**
     * Constructs a path from a specified argument.<br>
     * This method is useful if the argument needed is to be used for I/O.
     * <p>
     * <strong>Note:</strong> this method does not check if the argument is a valid path, so make sure you validate the path.
     *
     * @param index the index of the arguments that is expected to be a path.
     * @return a path pointing to the location specified by the argument;
     */

    public @NotNull Path getPathFromArgument(int index) {
        return Path.of(arguments.get(index));
    }

    /**
     * Checks to see if the argument builder is empty.
     *
     * @return {@code true} if the builder is not empty.
     */

    @Contract(pure = true)
    private boolean builderHasData() {
        return argumentBuilder.length() != 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(arguments, token.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(arguments);
    }

    @Override
    public String toString() {
        return "Token" + arguments;
    }
}
