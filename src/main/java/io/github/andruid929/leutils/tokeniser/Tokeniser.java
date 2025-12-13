package io.github.andruid929.leutils.tokeniser;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Tokeniser {

    private final List<String> arguments;

    private final StringBuilder argumentBuilder;

    private Tokeniser(@NotNull String input) {
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
    public static @NotNull Tokeniser tokenise(@NotNull String input) {
        return new Tokeniser(input);
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

    @Contract(pure = true)
    private boolean builderHasData() {
        return argumentBuilder.length() != 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tokeniser tokeniser = (Tokeniser) o;
        return Objects.equals(arguments, tokeniser.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(arguments);
    }

    @Override
    public String toString() {
        return "Tokeniser{" + "arguments=" + arguments + '}';
    }
}
