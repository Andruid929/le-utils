package io.github.andruid929.leutils.tokeniser;

/**
 * Exception thrown when an input String has an unclosed double quote.
 *
 * @since 1.0
 * @author Andrew Jones
 * */

public class UnclosedQuoteException extends IllegalArgumentException {

    /**
     * The unclosed argument.
     * */

    private final String s;

    /**
     * Create a new exception instance with the specific missing quote argument.
     *
     * @param s the argument that wasn't closed.
     * */

    public UnclosedQuoteException(String s) {
        super(s);
        this.s = s;
    }

    @Override
    public String getMessage() {
        return "Expected closing quote for starting quote -> \"" + s;
    }
}
