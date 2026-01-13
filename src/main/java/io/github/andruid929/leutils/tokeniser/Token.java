package io.github.andruid929.leutils.tokeniser;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

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

    private final List<String> flags;

    private final List<String> options;

    /**
     * Argument string builder.
     */

    private final StringBuilder argumentBuilder;

    private static final Pattern FLAGS_PATTERN = Pattern.compile("^(?<!-)-[A-Za-z]"); //Single dash followed by a letter

    /**
     * Instantiate arguments list and builder and collect arguments from the String.
     *
     * @param input the String to collect argument from.
     */

    private Token(@NotNull String input) {
        arguments = new ArrayList<>();

        argumentBuilder = new StringBuilder();

        boolean insideQuotes = false;
        boolean isEscape = false;

        {
            for (char c : input.trim().toCharArray()) {

                if (isEscape && c == '"') {
                    argumentBuilder.append(c);

                    isEscape = false;

                    continue;

                } else if (isEscape) {
                    argumentBuilder.append('\\').append(c);

                    isEscape = false;

                    continue;
                }

                if (c == '\\') {
                    isEscape = true;
                    continue;
                }
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

        if (isEscape) {
            argumentBuilder.append('\\');
        }

        if (builderHasData()) { //Any arguments following a quoted argument
            arguments.add(argumentBuilder.toString());
        }

        if (insideQuotes) { //If this is true, there is an unclosed quote in the input String
            throw new UnclosedQuoteException(argumentBuilder.toString());
        }

        this.flags = arguments.stream()
                .filter(argument -> FLAGS_PATTERN.matcher(argument).find())
                .toList();

        this.options = arguments.stream()
                .filter(argument -> argument.startsWith("--"))
                .toList();
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
     * @implNote For an argument to count as a flag,
     * it must start with a single dash followed by a letter either uppercase or lowercase.
     * {@code -2} or {@code -#} are not considered flags in this implementation.
     *
     */

    public @NotNull @Unmodifiable List<String> getFlags() {
        return flags;
    }

    /**
     * Get the flag at the specified index.
     *
     * @param index       the index to get from the {@link #getFlags() list of flags}.
     * @param includeDash choose whether to include the dash or not
     * @return the flag at the specified index as a String.
     * @throws IndexOutOfBoundsException if the flags list is empty
     *                                   or the specified index does not exist.
     * @implNote A lone dash ({@code -}) does not count as a flag.
     * @see #getFlagValue(int)
     * @see #getFlags()
     */

    public @NotNull String getFlag(int index, boolean includeDash) {
        String flag = flags.get(index);

        if (includeDash) {
            return flag;
        }

        return flag.substring(1);
    }

    /**
     * Get the value represented by a flag.
     * <p>For example, {@code -d} would return {@code d} as the value.</p>
     *
     * @param index the index to get from the {@link #getFlags() list of flags}.
     * @return the value for the flag at the specified index.
     * @throws IndexOutOfBoundsException if the flags list is empty
     *                                   or the specified index does not exist.
     * @implNote A lone dash ({@code -}) does not count as a flag.
     * @see #getFlag(int, boolean)
     * @see #getFlags()
     */

    public char getFlagValue(int index) {
        return getFlag(index, false).charAt(0);
    }

    /**
     * Get options present, if any.
     * <p>
     * Options are arguments that start with a double dash (--).
     *
     * @return List of options as Strings or an empty list.
     * @implNote The options in the returned list include the two dashes.
     *
     */

    public @NotNull @Unmodifiable List<String> getOptions() {
        return options;
    }

    /**
     * Get an option from the {@link #getOptions() list of options}
     * and choose whether you want to include the dashes or not.
     *
     * @param index         the index to get the option within the options list.
     * @param includeDashes choose to include the dashes or not.
     * @return the option at the specified index.
     * @throws IndexOutOfBoundsException if the {@link #getOptions() options list} is empty
     *                                   or the specified index does not exist.
     * @see #getOptions()
     *
     */

    public @NotNull String getOption(int index, boolean includeDashes) {
        String option = getOptions().get(index);

        if (includeDashes) {
            return option;
        }

        return option.substring(2);
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
        return arguments.getFirst();
    }

    /**
     * Get the last argument found;
     *
     * @return the last argument if any;
     * @throws IndexOutOfBoundsException if {@link #hasNoArguments()} returns true;
     */

    public String getLastArgument() {
        return arguments.getLast();
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
        return !argumentBuilder.isEmpty();
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
