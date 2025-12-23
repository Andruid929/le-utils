package io.github.andruid929.leutils.tokeniser;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Token {

    private final List<String> arguments;

    private final StringBuilder argumentBuilder;

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

        if (builderHasData()) {
            arguments.add(argumentBuilder.toString());
        }

        if (insideQuotes) {
            throw new UnclosedQuoteException(argumentBuilder);
        }
    }

    @Contract("_ -> new")
    public static @NotNull Token tokenise(@NotNull String input) {
        return new Token(input);
    }

    public List<String> getArguments() {
        return arguments;
    }

    public int getNumberOfArguments() {
        return arguments.size();
    }

    public boolean hasNoArguments() {
        return arguments.isEmpty();
    }

    public String getArgument(int index) {
        return arguments.get(index);
    }

    public String getFirstArgument() {
        return arguments.get(0);
    }

    public String getLastArgument() {
        return arguments.get(arguments.size() - 1);
    }

    public boolean hasArguments(int numberOfDesiredArguments) {
        return arguments.size() == numberOfDesiredArguments;
    }

    public @NotNull Path getPathFromArgument(int index) {
        return Path.of(arguments.get(index));
    }

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
        return "Token{" + "arguments=" + arguments + '}';
    }
}
