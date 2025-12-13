package io.github.andruid929.leutils.tokeniser;

public class UnclosedQuoteException extends IllegalArgumentException {

    private final StringBuilder s;

    public UnclosedQuoteException(StringBuilder s) {
        super();
        this.s = s;
    }

    @Override
    public String getMessage() {
        String unclosed = s.toString();

        return "Expected closing quote at \"" + unclosed + "<-";
    }
}
